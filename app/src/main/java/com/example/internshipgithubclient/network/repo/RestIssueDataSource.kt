package com.example.internshipgithubclient.network.repo

import com.example.core.data.IssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestIssueDataSource (val repoApiService: RepoApiService):IssueDataSource {
    override suspend fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return repoApiService.getIssuesForRepo(repo.owner.login, repo.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }
    }
}