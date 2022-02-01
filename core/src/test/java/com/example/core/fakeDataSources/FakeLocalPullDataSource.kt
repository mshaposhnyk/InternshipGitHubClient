package com.example.core.fakeDataSources

import com.example.core.createTestPull
import com.example.core.data.LocalPullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

class FakeLocalPullDataSource: LocalPullDataSource {
    val listOfPulls = ArrayList<Pull>()
    val pullUser = HashMap<Pull,User>()

    override fun addPull(pull: Pull):Completable {
        listOfPulls.add(pull)
        return Completable.complete()
    }

    override fun deletePull(pull: Pull): Completable {
        listOfPulls.remove(pull)
        return Completable.complete()
    }

    override fun addPullUserCrossRef(pull: Pull, user: User): Completable {
        pullUser[pull] = user
        return Completable.complete()
    }

    override fun getRepoPulls(repo: Repo): Single<List<Pull>> {
        return Single.just(listOf(createTestPull(repo.owner.id), createTestPull(repo.owner.id)))
    }
}