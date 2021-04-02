package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

interface LocalIssueDataSource {
    fun addIssue(issue: Issue): Completable
    fun addIssueAssigneeCrossRef(issue: Issue, assignee: User): Completable
    fun deleteIssue(issue: Issue): Completable
    fun getRepoIssues(repo: Repo): Single<List<Issue>>
}