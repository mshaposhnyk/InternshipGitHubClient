package com.example.core.fakeDataSources

import com.example.core.createTestIssue
import com.example.core.data.RemoteIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Single

class FakeRemoteIssueDataSource : RemoteIssueDataSource {
    override fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return Single.just(listOf(createTestIssue(repo.owner.id),createTestIssue(repo.owner.id)))
    }
}