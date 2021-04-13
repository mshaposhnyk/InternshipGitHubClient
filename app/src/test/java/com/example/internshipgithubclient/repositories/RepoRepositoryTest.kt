package com.example.internshipgithubclient.repositories

import com.example.core.data.RepoRepository
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeLocalRepoDataSource
import com.example.internshipgithubclient.repositories.fakeDataSources.FakeRemoteRepoDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class RepoRepositoryTest {
    private val repoRepository =
        RepoRepository(FakeRemoteRepoDataSource(), FakeLocalRepoDataSource())

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `get user repos`() = runBlockingTest {
        //Given
        val givenUser = createTestUser(1)
        val givenRepos = listOf(createTestRepo(1, givenUser.id))
        //When
        val earnedRepos = repoRepository.getAllUserRepos(givenUser)
        //Then
        Assert.assertEquals(givenRepos, earnedRepos.toList())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get dedicated repo`() = runBlockingTest {
        //Given
        val givenUser = createTestUser(1)
        val givenRepoName = "cool_project"
        //When
        val earnedRepo = repoRepository.getDedicatedRepo(givenUser, givenRepoName)
        //Then
        Assert.assertTrue(earnedRepo.owner.id == givenUser.id && earnedRepo.name == givenRepoName)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo watchers`() = runBlockingTest {
        //Given
        val givenWatchers = listOf(createTestUser(1))
        val givenRepo = createTestRepo(1)
        //When
        val earnedWatchers = repoRepository.getWatchersRepo(givenRepo)
        //Then
        Assert.assertEquals(givenWatchers,earnedWatchers.toList())
    }
}