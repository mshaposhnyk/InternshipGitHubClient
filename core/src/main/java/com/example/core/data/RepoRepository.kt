package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User

class RepoRepository(private val dataSource: RepoDataSource) {
    suspend fun getAllUserRepos(user: User): List<Repo> = dataSource.getAll(user)
    suspend fun getDedicatedRepo(user: User, nameRepo: String): Repo =
        dataSource.get(user, nameRepo)
    suspend fun getWatchersRepo(repo: Repo): List<User> = dataSource.getWatchers(repo)
}