package com.example.internshipgithubclient

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.domain.Pull
import com.example.core.domain.User
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.pull.PullDao
import com.example.internshipgithubclient.db.pull.PullRoomEntity
import com.example.internshipgithubclient.db.pull.PullWithAssignees
import com.example.internshipgithubclient.db.pull.PullsUsersCrossRef
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PullDaoTest {
    private lateinit var db: InternGitHubClientDatabase
    private lateinit var userDao: UserDao
    private lateinit var pullDao: PullDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InternGitHubClientDatabase::class.java
        )
            .allowMainThreadQueries().build()
        userDao = db.userDao()
        pullDao = db.pullDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun addPullWtAssigneeAndRead() {
        runBlocking {
            val user: UserRoomEntity =
                User(
                    1, "", "",
                    "", "", 1,
                    2, "", "",
                    "", "", 3,
                    4
                ).fromDomain(false)
            val pull: PullRoomEntity =
                PullRoomEntity(
                    1, "", 2,
                    "", "", "",
                    1, 1
                )
            val listAssignees = listOf(user)
            val pullWithAssignees = PullWithAssignees(pull, listAssignees)
            userDao.addUser(user)
            pullDao.addPull(pull)
            pullDao.addPullsAssigneeCrossRef(PullsUsersCrossRef(1,1))
            val getPullWUsers = pullDao.getPullWithAssignees(1)
            assertEquals(pullWithAssignees,getPullWUsers)
        }
    }

    @Test
    fun addPullWtAssigneeAndReadMocked(){

    }

}