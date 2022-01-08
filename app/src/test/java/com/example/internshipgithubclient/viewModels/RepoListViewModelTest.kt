package com.example.internshipgithubclient.viewModels

import com.example.core.domain.ErrorEntity
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import com.example.core.interactors.GetAllUserRepos
import com.example.core.interactors.GetUser
import com.example.internshipgithubclient.CoroutineTestRule
import com.example.internshipgithubclient.ui.workspace.repoList.RepoListViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
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
class RepoListViewModelTest {
    @Mock
    private lateinit var getUser: GetUser

    @Mock
    private lateinit var getAllUserRepos: GetAllUserRepos
    private lateinit var viewModel: RepoListViewModel

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
    fun `get authorised user success`() = runBlockingTest {
        //Given
        val authUser = createTestUser()
        whenever(getUser.invoke()).thenReturn(Result.Success(authUser))
        viewModel = RepoListViewModel(getUser, getAllUserRepos)
        //When
        viewModel.eventGotUser()
        //Then
        Assert.assertEquals(authUser, viewModel.userEntity)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get authorized user service error`() = runBlockingTest {
        //Given
        whenever(getUser.invoke()).thenReturn(Result.Error(ErrorEntity.ServiceUnavailable))
        viewModel = RepoListViewModel(getUser, getAllUserRepos)
        //When
        viewModel.eventGotUser()
        //Then
        Assert.assertEquals(true, viewModel.isAuthErrorOccurred.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo list success`() = runBlockingTest {
        //Given
        val authUser = createTestUser()
        whenever(getUser.invoke()).thenReturn(Result.Success(authUser))
        val flowRepos = flowOf(createTestRepo(), createTestRepo())
        whenever(getAllUserRepos.invoke(any())).thenReturn(Result.Success(flowRepos))
        viewModel = RepoListViewModel(getUser, getAllUserRepos)
        //When
        viewModel.eventGotUser()
        viewModel.fetchUserRepos(viewModel.userEntity)
        //Then
        viewModel.reposState.onEach { Assert.assertEquals(flowRepos.toList(),it) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get repo list service error`()= runBlockingTest {
        //Given
        val authUser = createTestUser()
        whenever(getUser.invoke()).thenReturn(Result.Success(authUser))
        whenever(getAllUserRepos.invoke(any())).thenReturn(Result.Error(ErrorEntity.Unknown))
        viewModel = RepoListViewModel(getUser, getAllUserRepos)
        //When
        viewModel.eventGotUser()
        viewModel.fetchUserRepos(viewModel.userEntity)
        //Then
        Assert.assertEquals(true,viewModel.isReposLoadErrorOccurred.value)
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

    private fun createTestRepo() = Repo(
        id = 1,
        url = "",
        description = "",
        forks = 1,
        forksCount = 1,
        fullName = "",
        name = "",
        owner = createTestUser(),
        stargazersCount = 2,
        openIssuesCount = 1,
        watchersCount = 2
    )
}