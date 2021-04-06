package com.example.internshipgithubclient.network.repo

import com.example.core.data.RemoteRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestRemoteRepoDataSource(val repoApiService: RepoApiService) : RemoteRepoDataSource {
    override fun getAllRepos(user: User): Single<List<Repo>> {
        return repoApiService.getUserRepos(user.login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }

    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return repoApiService.getRepo(user.login, repoName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Single.just(it.toDomain()) }
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return repoApiService.getWatchersForRepo(repo.owner.login, repo.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Single.just(it.toDomain())
            }
    }
}