package com.example.internshipgithubclient

import com.example.core.data.LocalRepoDataSource
import com.example.core.data.RemoteRepoDataSource
import com.example.core.data.RepoRepository
import com.example.core.domain.Repo
import com.example.core.domain.User
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import org.junit.Assert.assertEquals
import org.mockito.Mockito.doAnswer

@RunWith(MockitoJUnitRunner::class)
class RepoRepositoryTest {
    @Mock
    private lateinit var remoteRepoDataSource: RemoteRepoDataSource

    @Mock
    private lateinit var localRepoDataSource: LocalRepoDataSource

    @Test
    fun getAllUserReposTest() {
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
            val repos = listOf(
                Repo(
                    1,
                    "",
                    "",
                    2,
                    2,
                    "",
                    "",
                    user,
                    1,
                    2,
                    2
                )
            )
            val localRepos:ArrayList<Repo> = ArrayList()
            `when`(remoteRepoDataSource.getAllRepos(user)).thenReturn(repos.asFlow())
            doAnswer{
                localRepos.add(repos[0])
                return@doAnswer null
            }.`when`(localRepoDataSource).addRepo(repos[0])
            `when`(localRepoDataSource.get(user, repos[0].name)).thenReturn(repos[0])

            val repository = RepoRepository(remoteRepoDataSource,localRepoDataSource)
            val gotRepos = repository.getAllUserRepos(user).toList()
            assertEquals(repos,gotRepos )
        }
    }
}