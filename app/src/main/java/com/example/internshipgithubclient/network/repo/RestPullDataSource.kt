package com.example.internshipgithubclient.network.repo

import com.example.core.data.PullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestPullDataSource(val repoApiService: RepoApiService) : PullDataSource {
    override suspend fun getAll(repo: Repo): Single<List<Pull>> {
        return repoApiService.getPullsForRepo(repo.owner.login, repo.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }
    }
}