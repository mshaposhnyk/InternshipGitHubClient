package com.example.core.data

import com.example.core.createTestPull
import com.example.core.createTestRepo
import com.example.core.fakeDataSources.FakeLocalPullDataSource
import com.example.core.fakeDataSources.FakeLocalUserDataSource
import com.example.core.fakeDataSources.FakeRemotePullDataSource
import org.junit.Assert
import org.junit.Test

class PullRepositoryTest {

    private val pullRepository = PullRepository(
        FakeRemotePullDataSource(),
        FakeLocalPullDataSource(),
        FakeLocalUserDataSource()
    )

    @Test
    fun `get pulls for repo`() {
        val repo = createTestRepo(1)
        val givenRemotePulls = listOf(
            createTestPull(repo.owner.id),
            createTestPull(repo.owner.id)
        )

        val earnedRemotePulls = pullRepository.getRepoPulls(repo)

        Assert.assertEquals(givenRemotePulls.toList(), earnedRemotePulls.blockingGet())
    }
}