package com.example.internshipgithubclient.repositories

import com.example.core.data.IssueRepository
import com.example.core.domain.Repo
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.createTestIssue
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeLocalIssueDataSource
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeLocalUserDataSource
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeRemoteIssueDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class IssueRepositoryTest {

    private val issueRepository = IssueRepository(
        FakeRemoteIssueDataSource(),
        FakeLocalIssueDataSource(),
        FakeLocalUserDataSource()
    )

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `get issues for repo`() = runBlockingTest {
        //Given
        val repo = createTestRepo(1)
        val givenRemoteIssues = listOf(
            createTestIssue(repo.owner.id),
            createTestIssue(repo.owner.id)
        ).asFlow()
        //When
        val earnedRemoteIssues = issueRepository.getRepoIssues(repo)
        //Then
        Assert.assertEquals(givenRemoteIssues.toList(),earnedRemoteIssues.toList())
    }
}