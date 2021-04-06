package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface RemoteRepoDataSource {
    suspend fun getAllRepos(user: User): Flow<Repo>
    suspend fun get(user: User, repoName: String): Repo
    suspend fun getWatchers(repo: Repo): Flow<User>
}