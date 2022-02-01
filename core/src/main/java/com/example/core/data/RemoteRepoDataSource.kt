package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

interface RemoteRepoDataSource {
    fun getAllRepos(user: User): Single<List<Repo>>
    fun get(user: User,repoName:String): Single<Repo>
    fun getWatchers(repo: Repo):Single<List<User>>
}