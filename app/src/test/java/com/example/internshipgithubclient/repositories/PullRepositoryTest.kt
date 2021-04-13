package com.example.internshipgithubclient.repositories

import com.example.core.data.PullRepository
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.createTestPull
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.repositories.fakeDataSources.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PullRepositoryTest {

    private val pullRepository = PullRepository(
        FakeRemotePullDataSource(),
        FakeLocalPullDataSource(),
        FakeLocalUserDataSource()
    )

    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `get pulls for repo`() = runBlockingTest {
        //Given
        val repo = createTestRepo(1)
        val givenRemotePulls = listOf(
            createTestPull(repo.owner.id),
            createTestPull(repo.owner.id)
        )
        //When
        val earnedRemotePulls = pullRepository.getRepoPulls(repo)
        //Then
        Assert.assertEquals(givenRemotePulls.toList(),earnedRemotePulls.toList())
    }
}