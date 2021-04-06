package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface LocalIssueDataSource {
    suspend fun addIssue(issue: Issue)
    suspend fun addIssueAssigneeCrossRef(issue: Issue, assignee: User)
    suspend fun deleteIssue(issue: Issue)
    suspend fun getRepoIssues(repo: Repo): Flow<Issue>
}