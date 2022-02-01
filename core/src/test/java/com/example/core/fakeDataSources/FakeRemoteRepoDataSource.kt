package com.example.core.fakeDataSources

import com.example.core.createTestRepo
import com.example.core.createTestUser
import com.example.core.data.RemoteRepoDataSource
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Single

class FakeRemoteRepoDataSource : RemoteRepoDataSource {
    override fun getAllRepos(user: User): Single<List<Repo>> {
        return Single.just(listOf(createTestRepo(1,user.id)))
    }

    override fun get(user: User, repoName: String): Single<Repo> {
        return Single.just(createTestRepo(1,user.id,repoName))
    }

    override fun getWatchers(repo: Repo): Single<List<User>> {
        return Single.just(listOf(createTestUser(1)))
    }
}