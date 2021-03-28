package com.example.internshipgithubclient.db.repo

import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import io.reactivex.Single

class RoomRepoDataSource(private val repoDao: RepoDao, private val userDao: UserDao) :
    LocalRepoDataSource {
    override fun getAll(user: User): Single<List<Repo>> {
        return Single.just(repoDao.getAllUserRepos(user.id))
            .flatMap { list -> Single.just(list.map { it.toDomain(user) }) }

    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return Single.just(repoDao.getDedicatedRepo(user.id, repoName))
            .map { it.toDomain(user) }
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return Single.just(repoDao.getRepoWithWatchers(repo.id))
            .flatMap { repoWithWatchers -> Single.just(repoWithWatchers.userRoomEntities.map { it.toDomain() }) }
    }

    override fun addRepo(repo: Repo) {
        val authorizedUser = userDao.getAuthorizedUser()
        var isCurrentUser = false
        //Check if adding repo belongs to authorized user
        if (repo.owner.id == authorizedUser.userId) {
            isCurrentUser = true
        }
        //Adding new user to database or replacing old one if its exist
        userDao.addUser(repo.owner.fromDomain(isCurrentUser))
        //Adding new repo or replacing old one
        repoDao.addRepo(repo.fromDomain())
    }

    override fun deleteRepo(repo: Repo) {
        repoDao.deleteRepo(repo.fromDomain())
    }

}