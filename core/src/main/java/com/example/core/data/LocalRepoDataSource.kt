package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

interface LocalRepoDataSource {
    fun getAll(user: User): Single<List<Repo>>
    fun get(user: User, repoName:String): Single<Repo>
    fun getWatchers(repo: Repo): Single<List<User>>
    fun addRepo(repo: Repo)
    fun deleteRepo(repo: Repo)
}