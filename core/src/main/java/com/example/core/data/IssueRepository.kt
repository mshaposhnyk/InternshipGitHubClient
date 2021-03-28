package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single

class IssueRepository(private val dataSourceRemote: RemoteIssueDataSource) {
    //   suspend fun getUserIssues(user: User): List<Issue> = dataSource.getUserIssues(user)
    fun getRepoIssues(repo: Repo): Single<List<Issue>> = dataSourceRemote.getRepoIssues(repo)
}