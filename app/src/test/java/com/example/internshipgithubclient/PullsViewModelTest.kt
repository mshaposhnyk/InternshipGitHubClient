package com.example.internshipgithubclient;

import com.example.core.domain.IssueState;
import com.example.core.domain.Pull;
import com.example.core.domain.Repo;
import com.example.core.domain.Result;
import com.example.core.domain.User;
import com.example.core.interactors.GetRepoPulls;
import com.example.internshipgithubclient.ui.workspace.repoPulls.PullsViewModel;
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;


@RunWith(MockitoJUnitRunner::class)
class PullsViewModelTest {
    @Mock
    lateinit var getRepoPulls: GetRepoPulls

    @Mock
    lateinit var repo: Repo

    @Test
    fun getRepoPulls() {
        runBlocking {
            val user = User(
                1,
                "",
                "",
                "",
                "",
                1,
                2,
                "",
                "",
                "",
                "",
                3,
                4
            )
            val pull =
                Pull(1,
                    2,
                    "",
                    IssueState.OPEN,
                    "",
                    "",
                    user,
                    null,
                    ArrayList <User>())
            `when`(getRepoPulls(repo)).thenReturn(Result.Success(listOf(pull).asFlow()))
            val viewModel = PullsViewModel(getRepoPulls)
            viewModel.fetchPulls(repo)
            assertEquals(listOf(pull), viewModel.pulls.value)
        }

    }
}