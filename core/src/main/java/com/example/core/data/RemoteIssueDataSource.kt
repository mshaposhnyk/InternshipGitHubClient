package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface RemoteIssueDataSource {
    //    suspend fun getUserIssues(user: User): List<Issue>
    suspend fun getRepoIssues(repo: Repo): Flow<Issue>
}