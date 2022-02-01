package com.example.core.interactors

import com.example.core.createTestRepo
import com.example.core.data.RepoRepository
import com.example.core.domain.Result
import com.example.core.domain.User
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
class GetWatchersRepoTest {
    private var repoRepository: RepoRepository = mock()
    private var getWatchersRepo: GetWatchersRepo = GetWatchersRepo(repoRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get watchers for repo with success`() {
        whenever(repoRepository.getWatchersRepo(any()))
            .thenReturn(Single.just(Result.Success(listOf())))

        val result = getWatchersRepo.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Success<List<User>>)
    }

    @Test
    fun `get watchers for repo with error`() {
        whenever(repoRepository.getWatchersRepo(any())).thenReturn(Single.create {
            throw IOException()
        })

        val result = getWatchersRepo.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Error<List<User>>)
    }
}