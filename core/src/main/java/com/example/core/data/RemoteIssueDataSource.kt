package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single

interface RemoteIssueDataSource {
    //    suspend fun getUserIssues(user: User): List<Issue>
    fun getRepoIssues(repo: Repo): Single<List<Issue>>
}