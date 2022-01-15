package com.example.core.interactors

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.data.PullRepository
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.nhaarman.mockitokotlin2.any
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetRepoPullsTest {
    private var pullRepository: PullRepository = mock()
    private var getRepoPulls: GetRepoPulls = GetRepoPulls(pullRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get pulls for repo with success`() {
        whenever(pullRepository.getRepoPulls(any()))
            .thenReturn(Single.just(Result.Success(listOf())))

        val result = getRepoPulls.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Success<List<Pull>>)
    }

    @Test
    fun `get pulls for repo with error`() {
        whenever(pullRepository.getRepoPulls(any()))
            .thenReturn(Single.create {
                throw IOException()
            })

        val result = getRepoPulls.invoke(createTestRepo(0)).blockingGet()

        Assert.assertTrue(result is Result.Error<List<Pull>>)
    }
}