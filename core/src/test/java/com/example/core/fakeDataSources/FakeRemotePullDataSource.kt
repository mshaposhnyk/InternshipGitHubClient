package com.example.core.fakeDataSources

import com.example.core.createTestPull
import com.example.core.data.RemotePullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Single

class FakeRemotePullDataSource : RemotePullDataSource {
    override fun getAll(repo: Repo): Single<List<Pull>> {
        return Single.just(listOf(createTestPull(repo.owner.id),createTestPull(repo.owner.id)))
    }
}