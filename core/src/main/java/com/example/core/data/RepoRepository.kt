package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

class RepoRepository(private val dataSource: RepoDataSource) {
    fun getAllUserRepos(user: User): Single<List<Repo>> = dataSource.getAll(user)
    fun getDedicatedRepo(user: User, nameRepo: String): Single<Repo> =
        dataSource.get(user, nameRepo)
    fun getWatchersRepo(repo: Repo): Single<List<User>> = dataSource.getWatchers(repo)
}