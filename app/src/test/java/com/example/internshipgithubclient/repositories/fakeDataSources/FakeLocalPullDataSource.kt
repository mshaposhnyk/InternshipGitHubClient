package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.LocalPullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.createTestPull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeLocalPullDataSource: LocalPullDataSource {
    val listOfPulls = ArrayList<Pull>()
    val pullUser = HashMap<Pull,User>()

    override suspend fun addPull(pull: Pull) {
        listOfPulls.add(pull)
    }

    override suspend fun deletePull(pull: Pull) {
        listOfPulls.remove(pull)
    }

    override suspend fun addPullUserCrossRef(pull: Pull, user: User) {
        pullUser[pull] = user
    }

    override suspend fun getRepoPulls(repo: Repo): Flow<Pull> {
        return listOf(createTestPull(repo.owner.id), createTestPull(repo.owner.id)).asFlow()
    }
}