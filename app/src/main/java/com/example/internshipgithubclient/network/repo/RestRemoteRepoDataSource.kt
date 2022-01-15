package com.example.internshipgithubclient.network.repo

import com.example.core.data.RemoteRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RestRemoteRepoDataSource(private val repoApiService: RepoApiService) : RemoteRepoDataSource {
    override fun getAllRepos(user: User): Single<List<Repo>> {
        return repoApiService.getUserRepos(user.login)
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }

    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return repoApiService.getRepo(user.login, repoName)
            .flatMap { Single.just(it.toDomain()) }
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return repoApiService.getWatchersForRepo(repo.owner.login, repo.name)
            .flatMap {
                Single.just(it.toDomain())
            }
    }
}