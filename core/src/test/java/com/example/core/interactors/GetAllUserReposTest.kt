package com.example.core.interactors

import com.example.core.createTestUser
import com.example.core.data.RepoRepository
import com.example.core.domain.Repo
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
class GetAllUserReposTest {
    private var repoRepository: RepoRepository = mock()
    private var getAllUserRepos: GetAllUserRepos = GetAllUserRepos(repoRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get user repos with success`() {
        whenever(repoRepository.getAllUserRepos(any()))
            .thenReturn(Single.just(Result.Success(listOf())))

        val result = getAllUserRepos.invoke(createTestUser(0)).blockingGet()

        Assert.assertTrue(result is Result.Success<List<Repo>>)
    }

    @Test
    fun `get user repos with error`() {
        whenever(repoRepository.getAllUserRepos(any())).thenReturn(Single.create {
            throw IOException()
        })

        val result = getAllUserRepos.invoke(createTestUser(0)).blockingGet()

        Assert.assertTrue(result is Result.Error<List<Repo>>)
    }
}