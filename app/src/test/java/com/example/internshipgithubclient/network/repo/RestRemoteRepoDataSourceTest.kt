package com.example.internshipgithubclient.network.repo

import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.network.createTestRemoteRepo
import com.example.internshipgithubclient.network.createTestRemoteUser
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
class RestRemoteRepoDataSourceTest {
    @Mock
    private lateinit var repoApiService: RepoApiService

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        val remoteSource = Single.just(listOf(createTestRemoteRepo(), createTestRemoteRepo()))
        whenever(repoApiService.getUserRepos(any()))
            .thenReturn(remoteSource)
        whenever(repoApiService.getRepo(any(),any()))
            .thenReturn(Single.just(createTestRemoteRepo()))
        whenever(repoApiService.getWatchersForRepo(any(),any()))
            .thenReturn(Single.just(
                listOf(
                    createTestRemoteUser(0),
                    createTestRemoteUser(1))
                )
            )
    }

    @Test
    fun `check that remote data converted to core data properly for the specific repo`() {
        val givenRepo = createTestRemoteRepo()

        val earnedRepo = RestRemoteRepoDataSource(repoApiService)
            .get(createTestUser(0),"")
            .blockingGet()

        Assert.assertEquals(givenRepo.toDomain(),earnedRepo)
    }

    @Test
    fun `check that remote data converted to core data properly for the list of repos`() {
        val givenRepos = listOf(
            createTestRemoteRepo().toDomain(),
            createTestRemoteRepo().toDomain()
        )

        val earnedRepos = RestRemoteRepoDataSource(repoApiService)
            .getAllRepos(createTestUser(0))
            .blockingGet()

        Assert.assertEquals(givenRepos, earnedRepos)
    }

    @Test
    fun `check that remote data converted to core data properly for the list of watchers`() {
        val givenUsers = listOf(
            createTestRemoteUser(0).toDomain(),
            createTestRemoteUser(1).toDomain(),
        )

        val earnedUsers = RestRemoteRepoDataSource(repoApiService)
            .getWatchers(createTestRepo(0))
            .blockingGet()

        Assert.assertEquals(givenUsers, earnedUsers)
    }
}