package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Observable
import io.reactivex.Single

class PullRepository(
    private val dataSourceRemote: RemotePullDataSource,
    private val dataSourceLocal: LocalPullDataSource,
    private val userDataSource: LocalUserDataSource
) {
    fun getRepoPulls(repo: Repo): Single<List<Pull>> {
        return dataSourceRemote.getAll(repo)
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .flatMap{
                userDataSource.add(it.assignee)
                    .andThen(Observable.just(it))
            }
            .flatMap {
                dataSourceLocal.addPull(it)
                    .andThen(Observable.just(it))
            }
            .flatMapCompletable { pull ->
                Observable.fromIterable(pull.assignees.filterNotNull())
                    .flatMap {
                        userDataSource.add(it)
                            .andThen(Observable.just(it))
                    }
                    .flatMapCompletable { user ->
                        dataSourceLocal.addPullUserCrossRef(pull,user)
                    }
            }
            .andThen(dataSourceLocal.getRepoPulls(repo))
            .onErrorResumeNext (dataSourceLocal.getRepoPulls(repo))
    }
}