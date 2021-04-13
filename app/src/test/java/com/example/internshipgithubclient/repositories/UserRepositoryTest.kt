package com.example.internshipgithubclient.repositories

import com.example.core.data.UserRepository
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeLocalUserDataSource
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeRemoteUserDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class UserRepositoryTest {
    private var userRepository =
        UserRepository(FakeRemoteUserDataSource(), FakeLocalUserDataSource())

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `get authenticated user`() = runBlockingTest {
        //Given
        val givenUser = createTestUser(1)
        //When
        val earnedUser = userRepository.getUser()
        //Then
        Assert.assertEquals(givenUser,earnedUser)
    }
}