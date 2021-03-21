package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

interface RepoDataSource {
    suspend fun getAll(user: User): Single<List<Repo>>
    suspend fun get(user: User,repoName:String): Single<Repo>
    suspend fun getWatchers(repo: Repo):Single<List<User>>
}