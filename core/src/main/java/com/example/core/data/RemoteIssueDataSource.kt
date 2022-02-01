package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface RemoteIssueDataSource {
    fun getRepoIssues(repo: Repo): Single<List<Issue>>}