package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.RemoteRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.createTestRepo
import com.example.internshipgithubclient.createTestUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeRemoteRepoDataSource : RemoteRepoDataSource {
    override suspend fun getAllRepos(user: User): Flow<Repo> {
        return listOf(createTestRepo(1,user.id)).asFlow()
    }

    override suspend fun get(user: User, repoName: String): Repo {
        return createTestRepo(1,user.id,repoName)
    }

    override suspend fun getWatchers(repo: Repo): Flow<User> {
        return listOf(createTestUser(1)).asFlow()
    }
}