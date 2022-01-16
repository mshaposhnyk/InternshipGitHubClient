package com.example.internshipgithubclient.network.repo

import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.network.createTestRemoteIssue
import com.example.internshipgithubclient.network.toDomain
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestRemoteIssueDataSourceTest {
    @Mock
    private lateinit var repoApiService: RepoApiService

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `check that remote data converted to core data properly`() {
        val firstIssue = createTestRemoteIssue()
        val secondIssue = createTestRemoteIssue()
        val remoteSource = Single.just(listOf(firstIssue, secondIssue))
        whenever(repoApiService.getIssuesForRepo(any(), any(), any()))
            .thenReturn(remoteSource)

        val earnedList = RestRemoteIssueDataSource(repoApiService)
            .getRepoIssues(createTestRepo(0))
            .blockingGet()

        Assert.assertEquals(listOf(firstIssue.toDomain(),secondIssue.toDomain()),earnedList)
    }
}