package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Observable
import io.reactivex.Single

class RepoRepository(
    private val dataSourceRemote: RemoteRepoDataSource,
    private val dataSourceLocal: LocalRepoDataSource,
) {
    fun getAllUserRepos(user: User): Single<List<Repo>> {
        return dataSourceRemote.getAllRepos(user)
            .flatMap { repoList ->
                Observable.just(repoList)
                    .flatMapIterable { it }
                    .flatMap {
                        dataSourceLocal.addRepo(it)
                            .andThen(dataSourceLocal.addRepoWatcher(it))
                            .andThen(Observable.just(it))
                    }
                    .toList()
            }
            .onErrorResumeNext { dataSourceLocal.getAll(user) }
    }

    fun getDedicatedRepo(user: User, nameRepo: String): Single<Repo> =
        dataSourceRemote.get(user, nameRepo)

    fun getWatchersRepo(repo: Repo): Single<List<User>> =
        dataSourceRemote.getWatchers(repo).onErrorResumeNext {
            dataSourceLocal.getWatchers(repo)
        }
}