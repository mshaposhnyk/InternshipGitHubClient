package com.example.core.interactors

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.data.RepoRepository
import com.example.core.domain.Repo
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import com.example.core.domain.Result
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetDedicatedRepoTest {
    private var repoRepository: RepoRepository = mock()
    private var getDedicatedRepo: GetDedicatedRepo = GetDedicatedRepo(repoRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get dedicated repo with success`() {
        val testUser = createTestUser(0)
        val testRepo = createTestRepo(testUser.id)
        whenever(repoRepository.getDedicatedRepo(any(), any()))
            .thenReturn(Single.just(Result.Success(testRepo)))

        val result = getDedicatedRepo.invoke(testUser,testRepo.name).blockingGet()

        Assert.assertTrue(result is Result.Success<Repo>)
    }

    @Test
    fun `get dedicated repo with error`() {
        val testUser = createTestUser(0)
        val testRepo = createTestRepo(testUser.id)
        whenever(repoRepository.getDedicatedRepo(any(), any()))
            .thenReturn(Single.create {
                throw IOException()
            })

        val result = getDedicatedRepo.invoke(testUser,testRepo.name).blockingGet()

        Assert.assertTrue(result is Result.Error<Repo>)
    }
}