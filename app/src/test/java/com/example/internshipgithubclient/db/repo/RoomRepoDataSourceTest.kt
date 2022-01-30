package com.example.internshipgithubclient.db.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(sdk=[28])
class RoomRepoDataSourceTest {
    private lateinit var userDao: UserDao
    private lateinit var db: InternGitHubClientDatabase
    private lateinit var roomRepoDataSource: RoomRepoDataSource

    @get:Rule
    var instantTask = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, InternGitHubClientDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
        val repoDao = db.repoDao()
        roomRepoDataSource = RoomRepoDataSource(repoDao,userDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadRepo(){
        val repoOwner = createTestUser(0)
        val givenRepo = createTestRepo(0,repoOwner)

        roomRepoDataSource
            .addRepo(givenRepo)
            .andThen(roomRepoDataSource.get(repoOwner,givenRepo.name))
            .test()
            .assertComplete()
            .assertValue(givenRepo)
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndReadRepoWithAuthorizedUser(){
        val repoOwner = createTestUser(0)
        val givenRepo = createTestRepo(0,repoOwner)

        userDao.addUser(repoOwner.fromDomain(true))
            .andThen(
                roomRepoDataSource
                    .addRepo(givenRepo)
                    .andThen(roomRepoDataSource.get(repoOwner,givenRepo.name))
            )
            .test()
            .assertComplete()
            .assertValue(givenRepo)
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun readAllRepos(){
        val givenUser = createTestUser(0)
        val givenList = listOf(
            createTestRepo(0,givenUser),
            createTestRepo(1,givenUser)
        )

        Single.just(givenList)
            .flatMapCompletable { repoList ->
                Observable.just(repoList)
                    .flatMapIterable { it }
                    .flatMapCompletable {
                        roomRepoDataSource.addRepo(it)
                    }
            }
            .andThen(roomRepoDataSource.getAll(givenUser))
            .test()
            .assertResult(givenList)
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addRepoWatcherWithRepoAndRead() {
        val givenOwner = createTestUser(0)
        val givenRepo = createTestRepo(0,givenOwner)

        userDao.addUser(givenOwner.fromDomain(true))
            .andThen(roomRepoDataSource.addRepo(givenRepo))
            .andThen(roomRepoDataSource.addRepoWatcher(givenRepo))
            .andThen(roomRepoDataSource.getWatchers(givenRepo))
            .test()
            .assertValue(listOf(givenOwner))
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addRepoWatcherWithUserAndRepoAndRead() {
        val givenOwner = createTestUser(0)
        val givenFirstWatcher = createTestUser(1)
        val givenSecondWatcher = createTestUser(2)
        val givenRepo = createTestRepo(0,givenOwner)

        userDao.addUser(givenOwner.fromDomain(true))
            .andThen(roomRepoDataSource.addRepo(givenRepo))
            .andThen(roomRepoDataSource.addRepoWatcher(givenRepo,givenFirstWatcher))
            .andThen(roomRepoDataSource.addRepoWatcher(givenRepo,givenSecondWatcher))
            .andThen(roomRepoDataSource.getWatchers(givenRepo))
            .test()
            .assertValue(listOf(givenOwner))
            .dispose()
    }

    @Test
    @Throws(Exception::class)
    fun addAndDeleteRepo() {
        val givenOwner = createTestUser(0)
        val givenRepo = createTestRepo(0,givenOwner)

        userDao.addUser(givenOwner.fromDomain(true))
            .andThen(roomRepoDataSource.addRepo(givenRepo))
            .andThen(roomRepoDataSource.deleteRepo(givenRepo))
            .test()
            .assertComplete()
            .dispose()
    }
}