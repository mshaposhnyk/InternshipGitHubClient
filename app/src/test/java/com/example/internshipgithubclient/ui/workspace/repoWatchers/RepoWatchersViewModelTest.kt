package com.example.internshipgithubclient.ui.workspace.repoWatchers

import com.example.core.domain.ErrorEntity
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetWatchersRepo
import com.example.internshipgithubclient.RxImmediateSchedulerRule
import com.example.internshipgithubclient.ui.workspace.repoWatchers.RepoWatchersViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoWatchersViewModelTest {
    @Mock
    private lateinit var getWatchersRepo: GetWatchersRepo

    @Mock
    private lateinit var repo: Repo

    private lateinit var viewModel: RepoWatchersViewModel

    @Rule
    @JvmField
    val rxTestRule= RxImmediateSchedulerRule()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get repo watchers of repo success`() {
        //Given
        val firstTestWatcher = createTestUser()
        val secondTestWatcher = createTestUser()
        whenever(getWatchersRepo.invoke(any())).thenReturn(
            Single.just(Result.Success(listOf(firstTestWatcher,secondTestWatcher)))
        )
        viewModel = RepoWatchersViewModel(getWatchersRepo)
        //When
        viewModel.fetchWatchers(repo)
        //Then
        Assert.assertEquals(listOf(firstTestWatcher,secondTestWatcher),viewModel.listWatchers.value)
    }

    @Test
    fun `get repo service error occurred`() {
        //Given
        whenever(getWatchersRepo.invoke(any())).thenReturn(
            Single.just(Result.Error(ErrorEntity.ServiceUnavailable))
        )
        viewModel = RepoWatchersViewModel(getWatchersRepo)
        //When
        viewModel.fetchWatchers(repo)
        //Then
        Assert.assertEquals(viewModel.isFetchingWatchersFailed.value,true)
    }

    private fun createTestUser() = User(
        id = 1,
        avatarUrl = "",
        bio = "",
        company = "",
        email = "",
        followers = 1,
        following = 2,
        gistsUrl = "",
        location = "",
        login = "",
        name = "",
        publicGists = 3,
        publicRepos = 4
    )
}