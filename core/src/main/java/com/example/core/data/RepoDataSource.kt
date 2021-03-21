package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User

interface RepoDataSource {
    suspend fun getAll(user: User): List<Repo>
    suspend fun get(user: User,repoName:String): Repo
    suspend fun getWatchers(repo: Repo):List<User>
}