package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single
import kotlinx.coroutines.flow.*

class RepoRepository(
    private val dataSourceRemote: RemoteRepoDataSource,
    private val dataSourceLocal: LocalRepoDataSource,
) {
    suspend fun getAllUserRepos(user: User): Flow<Repo> {
        return dataSourceRemote.getAllRepos(user)
            .onEach { repo ->
                dataSourceLocal.addRepo(repo)
            }
            .map {
                dataSourceLocal.get(user, it.name)
            }
    }

    suspend fun getDedicatedRepo(user: User, nameRepo: String): Repo{
        val repo = dataSourceRemote.get(user, nameRepo)
        dataSourceLocal.addRepo(repo)
        return dataSourceLocal.get(user,nameRepo)
    }

    suspend fun getWatchersRepo(repo: Repo): Flow<User> {
        return dataSourceRemote.getWatchers(repo)
            .onEach {
                dataSourceLocal.addRepoWatcher(repo, it)
            }
            .flatMapConcat {
                dataSourceLocal.getWatchers(repo)
            }
    }

}