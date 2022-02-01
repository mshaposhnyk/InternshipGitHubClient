package com.example.internshipgithubclient.db.repo

import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.ui.createDummyUser
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class RoomRepoDataSource(private val repoDao: RepoDao, private val userDao: UserDao) :
    LocalRepoDataSource {

    override fun getAll(user: User): Single<List<Repo>> {
        return repoDao.getAllUserRepos(user.id)
            .flatMap { list -> Single.just(list.map { it.toDomain(user) }) }

    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return repoDao.getDedicatedRepo(user.id, repoName)
            .map { it.toDomain(user) }
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return repoDao.getRepoWithWatchers(repo.id)
            .flatMap { repoWithWatchers ->
                Single.just(repoWithWatchers.userRoomEntities.map {
                    it.toDomain()
                }) }
    }

    override fun addRepoWatcher(repo: Repo, user: User): Completable {
        return userDao.getAuthorizedUser()
            .flatMapCompletable {
                userDao.addUser(user.fromDomain(it.userId == user.id))
            }
            .onErrorResumeNext {
                userDao.addUser(user.fromDomain(false))
            }
            .andThen(addRepoWatcher(repo))
    }

    override fun addRepo(repo: Repo): Completable {
        return userDao.getUserById(repo.owner.id)
            .flatMapCompletable {
                repoDao.addRepo(repo.fromDomain())
            }
            .onErrorResumeNext {
                userDao.getAuthorizedUser()
                    .onErrorReturnItem(createDummyUser().fromDomain(true))
                    .map {
                        it.toDomain()
                    }
                    .flatMap {
                        Single.just(it.id == repo.owner.id)
                    }
                    .flatMapCompletable {
                        userDao.addUser(repo.owner.fromDomain(it))
                    }
                    .andThen(
                        repoDao.addRepo(repo.fromDomain())
                    )
            }
    }

    override fun addRepoWatcher(repo: Repo): Completable {
        val repoWatcher = ReposUsersCrossRef(repo.id,repo.owner.id)
        return repoDao.addRepoWatcher(repoWatcher)
    }

    override fun deleteRepo(repo: Repo): Completable {
        return repoDao.deleteRepo(repo.fromDomain())
    }
}
