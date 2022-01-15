package com.example.core.data

import com.example.core.createTestIssue
import com.example.core.createTestRepo
import com.example.core.fakeDataSources.FakeLocalIssueDataSource
import com.example.core.fakeDataSources.FakeLocalUserDataSource
import com.example.core.fakeDataSources.FakeRemoteIssueDataSource
import org.junit.Assert
import org.junit.Test
import com.example.core.domain.Result


class IssueRepositoryTest {
    private val issueRepository = IssueRepository(
        FakeRemoteIssueDataSource(),
        FakeLocalIssueDataSource(),
        FakeLocalUserDataSource()
    )


    @Test
    fun `get issues for repo`()  {
        val repo = createTestRepo(1)
        val givenRemoteIssues = listOf(
            createTestIssue(repo.owner.id),
            createTestIssue(repo.owner.id)
        )

        val earnedRemoteIssues = (issueRepository.getRepoIssues(repo).blockingGet() as Result.Success)

        Assert.assertEquals(givenRemoteIssues,earnedRemoteIssues.data)
    }
}