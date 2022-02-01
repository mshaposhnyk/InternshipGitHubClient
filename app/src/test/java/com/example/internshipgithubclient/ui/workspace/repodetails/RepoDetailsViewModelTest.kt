package com.example.internshipgithubclient.ui.workspace.repodetails

import com.example.core.domain.*
import com.example.core.interactors.GetRepoPulls
import com.example.internshipgithubclient.RxImmediateSchedulerRule
import com.example.internshipgithubclient.ui.workspace.repoDetails.RepoDetailsViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.ArrayList

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsViewModelTest {
    @Mock
    private lateinit var getRepoPulls: GetRepoPulls
    @Mock
    private lateinit var repo: Repo
    private lateinit var viewModel:RepoDetailsViewModel

    @Rule
    @JvmField
    val rxTestRule= RxImmediateSchedulerRule()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get repo pulls success`() {
        //Given
        val pull1 = createTestPull()
        val pull2 = createTestPull()
        whenever(getRepoPulls.invoke(any())).thenReturn(
            Single.just(Result.Success(listOf(pull1, pull2)))
        )
        viewModel = RepoDetailsViewModel(getRepoPulls)
        //When
        viewModel.fetchPulls(repo)
        //Then
        Assert.assertEquals(listOf(pull1, pull2), viewModel.pulls.value)
    }

    @Test
    fun `get repo service error occurred`() {
        //Given
        whenever(getRepoPulls.invoke(any())).thenReturn(
            Single.just(Result.Error(ErrorEntity.ServiceUnavailable))
        )
        viewModel = RepoDetailsViewModel(getRepoPulls)
        //When
        viewModel.fetchPulls(repo)
        //Then
        Assert.assertEquals(viewModel.isPullsFetchingErrorOccurred.value,true)
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
    private fun createTestPull() = Pull(
        id = 1,
        number = 2,
        repoUrl = "",
        state = IssueState.OPEN,
        title = "",
        body = "",
        user = createTestUser(),
        assignee = null,
        assignees = ArrayList<User>()
    )
}