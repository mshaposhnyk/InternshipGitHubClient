package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single

interface LocalIssueDataSource {
    fun addIssue(issue:Issue)
    fun deleteIssue(issue: Issue)
    fun getRepoIssues(repo: Repo): Single<List<Issue>>
}