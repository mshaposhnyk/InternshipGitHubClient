package com.example.core.interactors

import com.example.core.createTestUser
import com.example.core.data.UserRepository
import com.example.core.domain.Result
import com.example.core.domain.User
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
class GetUserTest {
    private var userRepository: UserRepository = mock()
    private var getUser: GetUser = GetUser(userRepository)

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get user with success`() {
        whenever(userRepository.getUser())
            .thenReturn(Single.just(Result.Success(createTestUser(0))))

        val result = getUser.invoke().blockingGet()

        Assert.assertTrue(result is Result.Success<User>)
    }

    @Test
    fun `get user with error`() {
        whenever(userRepository.getUser())
            .thenReturn(Single.create {
                throw IOException()
            })

        val result = getUser.invoke().blockingGet()

        Assert.assertTrue(result is Result.Error<User>)
    }
}