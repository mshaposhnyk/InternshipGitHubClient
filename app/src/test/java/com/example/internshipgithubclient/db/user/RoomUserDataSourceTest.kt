package com.example.internshipgithubclient.db.user

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.createTestUser
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(sdk=[28])
class RoomUserDataSourceTest {
    private lateinit var db: InternGitHubClientDatabase
    private lateinit var roomUserDataSource: RoomUserDataSource

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InternGitHubClientDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        val userDao = db.userDao()
        roomUserDataSource = RoomUserDataSource(userDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadUserById() {
        val givenUser = createTestUser(0)

        roomUserDataSource.add(givenUser)
            .andThen(roomUserDataSource.getById(0))
            .test()
            .assertValue(givenUser)
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadAuthorizedUser() {
        val givenUser = createTestUser(0)

        roomUserDataSource.addAuthorized(givenUser)
            .andThen(roomUserDataSource.getAuthorized())
            .test()
            .assertValue(givenUser)
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndDeleteUser(){
        val givenUser = createTestUser(0)

        roomUserDataSource.add(givenUser)
            .andThen(roomUserDataSource.deleteUser(givenUser))
            .test()
            .assertComplete()
            .dispose()
    }
}