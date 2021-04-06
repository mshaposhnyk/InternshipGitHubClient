package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface LocalRepoDataSource {
    suspend fun getAll(user: User): Flow<Repo>
    suspend fun get(user: User, repoName: String): Repo
    suspend fun getWatchers(repo: Repo): Flow<User>
    suspend fun addRepo(repo: Repo)
    suspend fun addRepoWatcher(repo: Repo,user: User)
    suspend fun deleteRepo(repo: Repo)
}