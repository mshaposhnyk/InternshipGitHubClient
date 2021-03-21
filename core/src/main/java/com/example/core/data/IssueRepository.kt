package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User

class IssueRepository(private val dataSource: IssueDataSource) {
 //   suspend fun getUserIssues(user: User): List<Issue> = dataSource.getUserIssues(user)
    suspend fun getRepoIssues(repo: Repo): List<Issue> = dataSource.getRepoIssues(repo)
}