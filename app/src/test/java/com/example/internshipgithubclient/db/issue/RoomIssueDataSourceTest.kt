package com.example.internshipgithubclient.db.issue

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.internshipgithubclient.createTestIssue
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.repo.RepoDao
import com.example.internshipgithubclient.db.user.UserDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomIssueDataSourceTest {
    private lateinit var userDao: UserDao
    private lateinit var repoDao: RepoDao
    private lateinit var db: InternGitHubClientDatabase
    private lateinit var roomIssueDataSource: RoomIssueDataSource

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
        val issueDao = db.issueDao()
        roomIssueDataSource = RoomIssueDataSource(issueDao,userDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadIssue() {
        val givenAssignee = createTestUser(1)
        val givenUser = createTestUser(0)
        val givenRepo = createTestRepo(0,givenUser)
        val givenIssue = createTestIssue(givenUser, givenRepo,givenAssignee)

        userDao.addUser(givenUser.fromDomain(true))
            .andThen(repoDao.addRepo(givenRepo.fromDomain()))
            .andThen(userDao.addUser(givenAssignee.fromDomain(false)))
            .andThen(roomIssueDataSource.addIssue(givenIssue))
            .andThen(roomIssueDataSource.addIssueAssigneeCrossRef(givenIssue,givenAssignee))
            .andThen(roomIssueDataSource.getRepoIssues(givenRepo))
            .test()
            .assertValue(listOf(givenIssue))
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndDeleteIssue() {
        val givenAssignee = createTestUser(1)
        val givenUser = createTestUser(0)
        val givenRepo = createTestRepo(0,givenUser)
        val givenIssue = createTestIssue(givenUser, givenRepo,givenAssignee)

        userDao.addUser(givenUser.fromDomain(true))
            .andThen(repoDao.addRepo(givenRepo.fromDomain()))
            .andThen(userDao.addUser(givenAssignee.fromDomain(false)))
            .andThen(roomIssueDataSource.addIssue(givenIssue))
            .andThen(roomIssueDataSource.addIssueAssigneeCrossRef(givenIssue,givenAssignee))
            .andThen(roomIssueDataSource.getRepoIssues(givenRepo))
            .test()
            .assertComplete()
            .dispose()
    }
}