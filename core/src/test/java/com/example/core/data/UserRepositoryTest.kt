package com.example.core.data

import com.example.core.createTestUser
import com.example.core.fakeDataSources.FakeLocalUserDataSource
import com.example.core.fakeDataSources.FakeRemoteUserDataSource
import org.junit.Assert
import org.junit.Test

class UserRepositoryTest {
    private var userRepository =
        UserRepository(FakeRemoteUserDataSource(), FakeLocalUserDataSource())

    @Test
    fun `get authenticated user`() {
        val givenUser = createTestUser(1)

        val earnedUser = userRepository.getUser().blockingGet()

        Assert.assertEquals(givenUser,earnedUser)
    }
}