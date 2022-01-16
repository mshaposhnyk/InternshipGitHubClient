package com.example.internshipgithubclient.network.user

import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.network.createTestRemotePull
import com.example.internshipgithubclient.network.createTestRemoteUser
import com.example.internshipgithubclient.network.pull.RestRemotePullDataSource
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
class RestRemoteUserDataSourceTest {
    @Mock
    private lateinit var userApiService: UserApiService

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `check that remote data converted to core data properly`() {
        val givenUser = createTestRemoteUser()
        val remoteSource = Single.just(givenUser)
        whenever(userApiService.getAuthenticatedUser())
            .thenReturn(remoteSource)

        val earnedList = RestRemoteUserDataSource(userApiService)
            .get()
            .blockingGet()

        Assert.assertEquals(givenUser.toDomain(),earnedList)
    }
}