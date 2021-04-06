package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class IssueRepository(
    private val dataSourceRemote: RemoteIssueDataSource,
    private val dataSourceLocal: LocalIssueDataSource,
    private val userDataSource: LocalUserDataSource
) {
    suspend fun getRepoIssues(repo: Repo): Flow<Issue> {
        dataSourceRemote.getRepoIssues(repo)
            .onEach { issue ->
                userDataSource.add(issue.assignee)
                dataSourceLocal.addIssue(issue)
                issue.assignees.filterNotNull()
                    .forEach{
                        userDataSource.add(it)
                        dataSourceLocal.addIssueAssigneeCrossRef(issue,it)
                    }
            }.collect()
        return dataSourceLocal.getRepoIssues(repo)
    }
}