package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.RemotePullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.internshipgithubclient.createTestPull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeRemotePullDataSource : RemotePullDataSource {
    override suspend fun getAll(repo: Repo): Flow<Pull> {
        return listOf(createTestPull(repo.owner.id),createTestPull(repo.owner.id)).asFlow()
    }
}