package com.example.internshipgithubclient.network.repo

import com.example.core.data.RepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestRepoDataSource(val repoApiService: RepoApiService) : RepoDataSource {
    override suspend fun getAll(user: User): Single<List<Repo>> {
        return repoApiService.getUserRepos(user.login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }

    }

    override suspend fun get(user: User, repoName: String): Single<Repo> {
        return repoApiService.getRepo(user.login, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Single.just(it.toDomain()) }
    }

    override suspend fun getWatchers(repo: Repo): Single<List<User>> {
        return repoApiService.getWatchersForRepo(repo.owner.login, repo.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Single.just(it.toDomain()) }
    }
}