package com.example.internshipgithubclient.db.pull

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.internshipgithubclient.createTestPull
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.repo.RepoDao
import com.example.internshipgithubclient.db.repo.RoomRepoDataSource
import com.example.internshipgithubclient.db.user.UserDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(sdk=[28])
class RoomPullDataSourceTest {
    private lateinit var userDao: UserDao
    private lateinit var repoDao: RepoDao
    private lateinit var db: InternGitHubClientDatabase
    private lateinit var roomPullDataSource: RoomPullDataSource

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InternGitHubClientDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repoDao = db.repoDao()
        userDao = db.userDao()
        val pullDao = db.pullDao()
        roomPullDataSource = RoomPullDataSource(pullDao,userDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadPull() {
        val givenAssignee = createTestUser(1)
        val givenUser = createTestUser(0)
        val givenRepo = createTestRepo(0,givenUser)
        val givenPull = createTestPull(givenUser, givenRepo,givenAssignee)

        userDao.addUser(givenUser.fromDomain(true))
            .andThen(repoDao.addRepo(givenRepo.fromDomain()))
            .andThen(userDao.addUser(givenAssignee.fromDomain(false)))
            .andThen(roomPullDataSource.addPull(givenPull))
            .andThen(roomPullDataSource.addPullUserCrossRef(givenPull,givenAssignee))
            .andThen(roomPullDataSource.getRepoPulls(givenRepo))
            .test()
            .assertValue(listOf(givenPull))
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndDeletePull() {
        val givenAssignee = createTestUser(1)
        val givenUser = createTestUser(0)
        val givenRepo = createTestRepo(0,givenUser)
        val givenPull = createTestPull(givenUser, givenRepo,givenAssignee)

        userDao.addUser(givenUser.fromDomain(true))
            .andThen(repoDao.addRepo(givenRepo.fromDomain()))
            .andThen(userDao.addUser(givenAssignee.fromDomain(false)))
            .andThen(roomPullDataSource.addPull(givenPull))
            .andThen(roomPullDataSource.addPullUserCrossRef(givenPull,givenAssignee))
            .andThen(roomPullDataSource.deletePull(givenPull))
            .test()
            .assertComplete()
            .dispose()
    }
}