package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.RemoteIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.internshipgithubclient.createTestIssue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeRemoteIssueDataSource : RemoteIssueDataSource {
    override suspend fun getRepoIssues(repo: Repo): Flow<Issue> {
        return listOf(createTestIssue(repo.owner.id),createTestIssue(repo.owner.id)).asFlow()
    }
}