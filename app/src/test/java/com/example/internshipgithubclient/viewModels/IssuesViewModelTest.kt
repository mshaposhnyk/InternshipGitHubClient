package com.example.internshipgithubclient.viewModels

import com.example.core.domain.*
import com.example.core.interactors.GetRepoIssues
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
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
class IssuesViewModelTest {
    @Mock
    private lateinit var getRepoIssues: GetRepoIssues
    @Mock
    private lateinit var repo: Repo
    private lateinit var viewModel:IssuesViewModel

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
    fun `get repo issues success`() = runBlockingTest {
        //Given
        val issue1 = createTestIssue()
        val issue2 = createTestIssue()
        whenever(getRepoIssues.invoke(any())).thenReturn(Result.Success(listOf(issue1, issue2).asFlow()))
        viewModel = IssuesViewModel(getRepoIssues)
        //When
        viewModel.fetchIssues(repo)
        //Then
        Assert.assertEquals(listOf(issue1, issue2), viewModel.issues.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo service error occurred`() = runBlockingTest {
        //Given
        whenever(getRepoIssues.invoke(any())).thenReturn(Result.Error(ErrorEntity.ServiceUnavailable))
        viewModel = IssuesViewModel(getRepoIssues)
        //When
        viewModel.fetchIssues(repo)
        //Then
        Assert.assertEquals(viewModel.isIssuesFetchingErrorOccurred.value,true)
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
    private fun createTestIssue() = Issue(
        id = 1,
        number = 2,
        repoUrl = "",
        state = IssueState.OPEN,
        title = "",
        body = "",
        user = createTestUser(),
        assignee = null,
        assignees = ArrayList<User>(),
        commentsCount = 2
    )
}