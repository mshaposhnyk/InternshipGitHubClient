package com.example.core.data

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.fakeDataSources.FakeLocalRepoDataSource
import com.example.core.fakeDataSources.FakeRemoteRepoDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

class RepoRepositoryTest {
    private val repoRepository =
        RepoRepository(FakeRemoteRepoDataSource(), FakeLocalRepoDataSource())

    @Test
    fun `get user repos`() {
        val givenUser = createTestUser(1)
        val givenRepos = listOf(createTestRepo(1, givenUser.id))

        val earnedRepos = repoRepository.getAllUserRepos(givenUser).blockingGet()

        Assert.assertEquals(givenRepos, earnedRepos)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get dedicated repo`() {
        val givenUser = createTestUser(1)
        val givenRepoName = "cool_project"

        val earnedRepo = repoRepository.getDedicatedRepo(givenUser, givenRepoName).blockingGet()

        Assert.assertTrue(earnedRepo.owner.id == givenUser.id && earnedRepo.name == givenRepoName)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo watchers`() {
        val givenWatchers = listOf(createTestUser(1))
        val givenRepo = createTestRepo(1)

        val earnedWatchers = repoRepository.getWatchersRepo(givenRepo).blockingGet()

        Assert.assertEquals(givenWatchers,earnedWatchers.toList())
    }
}