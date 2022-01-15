package com.example.internshipgithubclient.network.repo

import com.example.core.data.RemoteIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class RestRemoteIssueDataSource (private val repoApiService: RepoApiService):RemoteIssueDataSource {
    override fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return repoApiService.getIssuesForRepo(repo.owner.login, repo.name)
            .flatMap { list -> Single.just(list.map { it.toDomain() }) }
    }
}