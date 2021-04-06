package com.example.internshipgithubclient.network.repo

import com.example.core.data.RemoteRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RestRemoteRepoDataSource(private val repoApiService: RepoApiService) : RemoteRepoDataSource {
    override suspend fun getAllRepos(user: User): Flow<Repo> {
        return repoApiService.getUserRepos(user.login).asFlow()
            .map {
                it.toDomain()
            }
    }

    override suspend fun get(user: User, repoName: String): Repo {
        return repoApiService.getRepo(user.login, repoName).toDomain()
    }

    override suspend fun getWatchers(repo: Repo): Flow<User> {
        return repoApiService.getWatchersForRepo(repo.owner.login, repo.name).asFlow()
            .map {
                it.toDomain()
            }.flowOn(Dispatchers.IO)
    }
}