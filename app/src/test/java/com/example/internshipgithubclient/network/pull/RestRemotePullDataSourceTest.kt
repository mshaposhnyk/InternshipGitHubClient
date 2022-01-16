package com.example.internshipgithubclient.network.pull

import com.example.core.domain.Repo
import com.example.internshipgithubclient.createTestPull
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.network.createTestRemotePull
import com.example.internshipgithubclient.network.repo.RepoApiService
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
class RestRemotePullDataSourceTest {
    @Mock
    private lateinit var repoApiService: RepoApiService

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `check that remote data converted to core data properly`() {
        val firstPull = createTestRemotePull()
        val secondPull = createTestRemotePull()
        val remoteSource = Single.just(listOf(firstPull, secondPull))
        whenever(repoApiService.getPullsForRepo(any(),any(),any()))
            .thenReturn(remoteSource)

        val earnedList = RestRemotePullDataSource(repoApiService)
            .getAll(createTestRepo(0))
            .blockingGet()

        Assert.assertEquals(listOf(firstPull.toDomain(),secondPull.toDomain()),earnedList)
    }
}