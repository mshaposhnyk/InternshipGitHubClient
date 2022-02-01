package com.example.core.interactors

import com.example.core.createTestRepo
import com.example.core.data.IssueRepository
import com.example.core.domain.Issue
import com.example.core.domain.Pull
import com.example.core.domain.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetRepoIssuesTest {
    private var issueRepository : IssueRepository = mock()
    private var getRepoIssues: GetRepoIssues = GetRepoIssues(issueRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get issues for repo with success`() {
        whenever(issueRepository.getRepoIssues(any()))
            .thenReturn(Single.just(Result.Success(listOf())))

        val result = getRepoIssues.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Success<List<Issue>>)
    }

    @Test
    fun `get issues for repo with error`() {
        whenever(issueRepository.getRepoIssues(any()))
            .thenReturn(Single.create {
                throw IOException()
            })

        val result = getRepoIssues.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Error<List<Issue>>)
    }
}