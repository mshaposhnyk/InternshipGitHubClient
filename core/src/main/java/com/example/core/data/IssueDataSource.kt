package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User

interface IssueDataSource {
//    suspend fun getUserIssues(user: User): List<Issue>
    suspend fun getRepoIssues(repo: Repo): List<Issue>
}