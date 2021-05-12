package com.example.internshipgithubclient.viewModels;

import com.example.core.domain.*
import com.example.core.interactors.GetRepoPulls;
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.ui.workspace.repoPulls.PullsViewModel;
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner::class)
class PullsViewModelTest {
    @Mock
    private lateinit var getRepoPulls: GetRepoPulls
    @Mock
    private lateinit var repo: Repo
    private lateinit var viewModel: PullsViewModel
    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()
    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }
    @ExperimentalCoroutinesApi
    @Test
    fun `get repo pulls success`() = runBlockingTest {
        //Given
        val pull1 = createTestPull()
        val pull2 = createTestPull()
        whenever(getRepoPulls.invoke(any())).thenReturn(Result.Success(listOf(pull1, pull2).asFlow()))
        viewModel = PullsViewModel(getRepoPulls)
        //When
        viewModel.fetchPulls(repo)
        //Then
        Assert.assertEquals(listOf(pull1, pull2), viewModel.pulls.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo service error occurred`() = runBlockingTest {
        //Given
        whenever(getRepoPulls.invoke(any())).thenReturn(Result.Error(ErrorEntity.ServiceUnavailable))
        viewModel = PullsViewModel(getRepoPulls)
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