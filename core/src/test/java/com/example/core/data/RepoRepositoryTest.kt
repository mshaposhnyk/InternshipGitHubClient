package com.example.core.data

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.fakeDataSources.FakeLocalRepoDataSource
import com.example.core.fakeDataSources.FakeRemoteRepoDataSource
import org.junit.Assert
import org.junit.Test

class RepoRepositoryTest {
    private val repoRepository =
        RepoRepository(FakeRemoteRepoDataSource(), FakeLocalRepoDataSource())

    @Test
    fun `get user repos`() {
        val givenUser = createTestUser(1)
        val givenRepos = listOf(createTestRepo(1, givenUser.id))

        val earnedRepos =
            repoRepository.getAllUserRepos(givenUser).blockingGet() as Result.Success<List<Repo>>

        Assert.assertEquals(givenRepos, earnedRepos.data)
    }

    @Test
    fun `get dedicated repo`() {
        val givenUser = createTestUser(1)
        val givenRepoName = "cool_project"

        val earnedRepo = (repoRepository.getDedicatedRepo(givenUser, givenRepoName).blockingGet() as Result.Success).data

        Assert.assertTrue(earnedRepo.owner.id == givenUser.id && earnedRepo.name == givenRepoName)
    }

    @Test
    fun `get repo watchers`() {
        val givenWatchers = listOf(createTestUser(1))
        val givenRepo = createTestRepo(1)

        val earnedWatchers = (repoRepository.getWatchersRepo(givenRepo).blockingGet() as Result.Success).data

        Assert.assertEquals(givenWatchers, earnedWatchers.toList())
    }
}