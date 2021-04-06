package com.example.internshipgithubclient.db.repo

import com.example.core.data.LocalRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class RoomRepoDataSource(private val repoDao: RepoDao, private val userDao: UserDao) :
    LocalRepoDataSource {


    override suspend fun getAll(user: User): Flow<Repo> {
        return repoDao.getAllUserRepos(user.id).asFlow()
            .map {
                val owner = userDao.getUserById(it.ownerId)
                it.toDomain(owner.toDomain())
            }

    }

    override suspend fun get(user: User, repoName: String): Repo {
        return repoDao.getDedicatedRepo(user.id, repoName).toDomain(user)
    }

    override suspend fun getWatchers(repo: Repo): Flow<User> {
        return repoDao.getRepoWithWatchers(repo.id).userRoomEntities.asFlow()
            .map { it.toDomain() }
    }

    override suspend fun addRepo(repo: Repo) {
        return repoDao.addRepo(repo.fromDomain())
    }

    override suspend fun addRepoWatcher(repo: Repo, user: User) {
        val repoWatcher = ReposUsersCrossRef(repo.id, user.id)
        repoDao.addRepoWatcher(repoWatcher)
    }

    override suspend fun deleteRepo(repo: Repo) {
        return repoDao.deleteRepo(repo.fromDomain())
    }

}
