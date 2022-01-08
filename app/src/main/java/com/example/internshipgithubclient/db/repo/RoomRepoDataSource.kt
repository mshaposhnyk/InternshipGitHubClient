package com.example.internshipgithubclient.db.repo

import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list -> Single.just(list.map { it.toDomain(user) }) }

    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return repoDao.getDedicatedRepo(user.id, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toDomain(user) }
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return repoDao.getRepoWithWatchers(repo.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { repoWithWatchers ->
                Single.just(repoWithWatchers.userRoomEntities.map {
                    it.toDomain()
                }) }
    }

    override fun addRepo(repo: Repo): Completable {
        return repoDao.addRepo(repo.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addRepoWatcher(repo: Repo): Completable {
        val repoWatcher = ReposUsersCrossRef(repo.id,repo.owner.id)
        return repoDao.addRepoWatcher(repoWatcher)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteRepo(repo: Repo): Completable {
        return repoDao.deleteRepo(repo.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
