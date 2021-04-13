package com.example.internshipgithubclient

import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.user.RoomUserDataSource
import com.example.internshipgithubclient.db.user.UserDao
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.junit.Assert.assertEquals


@RunWith(MockitoJUnitRunner::class)
class UserLocalDataSourceTest {
    @Mock
    private lateinit var userDao: UserDao

    @Test
    fun getUser() {
        runBlocking {
            val user = User(
                1,
                "",
                "",
                "",
                "",
                1,
                2,
                "",
                "",
                "",
                "",
                3,
                4
            )
            `when`(userDao.getAuthorizedUser()).thenReturn(
               user.fromDomain(true)
            )
            val roomUserDataSource = RoomUserDataSource(userDao)
            assertEquals(roomUserDataSource.getAuthorized(),user)
        }
    }
}
