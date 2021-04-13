package com.example.internshipgithubclient

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.domain.User
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var userDao: UserDao
    private lateinit var db: InternGitHubClientDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InternGitHubClientDatabase::class.java
        )
            .allowMainThreadQueries().build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeUserAuthAndRead() {
        runBlocking {
            val user: UserRoomEntity =
                User(1, "", "", "", "", 1, 2, "", "", "", "", 3, 4).fromDomain(true)
            userDao.addUser(user)
            val getUser = userDao.getAuthorizedUser()
            assertThat(user, equalTo(getUser))
        }
    }

    @Test
    fun writeUserAndReadById() {
        runBlocking {
            val user: UserRoomEntity =
                User(1, "", "", "", "", 1, 2, "", "", "", "", 3, 4).fromDomain(true)
            userDao.addUser(user)
            val getUser = userDao.getUserById(1)
            assertThat(user, equalTo(getUser))
        }
    }

    @Test
    fun writeUserAndDelete() {
        runBlocking {
            val user: UserRoomEntity =
                User(1, "", "", "", "", 1, 2, "", "", "", "", 3, 4).fromDomain(false)
            userDao.addUser(user)
            val getUser = userDao.getUserById(user.userId)
            assertEquals(user,getUser)
            userDao.deleteUser(user)
            val getUserDeleted = userDao.getUserById(user.userId)
            assertEquals(getUserDeleted,null)
        }
    }
}