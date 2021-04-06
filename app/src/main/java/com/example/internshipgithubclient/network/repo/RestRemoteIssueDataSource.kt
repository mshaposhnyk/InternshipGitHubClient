package com.example.internshipgithubclient.network.repo

import com.example.core.data.RemoteIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.internshipgithubclient.network.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class RestRemoteIssueDataSource (private val repoApiService: RepoApiService):RemoteIssueDataSource {
    override suspend fun getRepoIssues(repo: Repo): Flow<Issue> {
        return repoApiService.getIssuesForRepo(repo.owner.login, repo.name).asFlow()
            .map {
                it.toDomain()
            }
    }
}