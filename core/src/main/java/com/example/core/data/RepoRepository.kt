package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

class RepoRepository(private val dataSourceRemote: RemoteRepoDataSource) {
    fun getAllUserRepos(user: User): Single<List<Repo>> = dataSourceRemote.getAll(user)
    fun getDedicatedRepo(user: User, nameRepo: String): Single<Repo> =
        dataSourceRemote.get(user, nameRepo)
    fun getWatchersRepo(repo: Repo): Single<List<User>> = dataSourceRemote.getWatchers(repo)
}