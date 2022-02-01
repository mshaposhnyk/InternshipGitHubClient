package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Observable
import io.reactivex.Single
import com.example.core.domain.*


open class RepoRepository(
    private val dataSourceRemote: RemoteRepoDataSource,
    private val dataSourceLocal: LocalRepoDataSource,
) {
    open fun getAllUserRepos(user: User): Single<Result<List<Repo>>> {
        val remoteRepos = dataSourceRemote.getAllRepos(user)
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
        val localRepos = dataSourceLocal.getAll(user)
        return Single.concat(remoteRepos, localRepos)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }

    open fun getDedicatedRepo(user: User, nameRepo: String): Single<Result<Repo>> {
        val remoteRepo = dataSourceRemote.get(user, nameRepo)
            .flatMap {
                dataSourceLocal.addRepo(it)
                    .andThen(dataSourceLocal.addRepoWatcher(it))
                    .andThen(Single.just(it))
            }
        val localRepo = dataSourceLocal.get(user, nameRepo)
        return Single.concat(remoteRepo, localRepo)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }

    open fun getWatchersRepo(repo: Repo): Single<Result<List<User>>> {
        val remoteWatchers = dataSourceRemote.getWatchers(repo)
            .flatMap { userList ->
                Observable.just(userList)
                    .flatMapIterable { it }
                    .flatMap { user ->
                        dataSourceLocal.addRepoWatcher(repo, user)
                            .andThen(Observable.just(user))
                    }.toList()
            }
        val localWatchers = dataSourceLocal.getWatchers(repo)
        return Single.concat(remoteWatchers,localWatchers)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }
}