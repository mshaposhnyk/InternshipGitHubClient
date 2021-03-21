package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

interface IssueDataSource {
    //    suspend fun getUserIssues(user: User): List<Issue>
    suspend fun getRepoIssues(repo: Repo): Single<List<Issue>>
}