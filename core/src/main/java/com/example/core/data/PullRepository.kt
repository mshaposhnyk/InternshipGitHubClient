package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class PullRepository(
    private val dataSourceRemote: RemotePullDataSource,
    private val dataSourceLocal: LocalPullDataSource,
    private val userDataSource: LocalUserDataSource
) {
    fun getRepoPulls(repo: Repo): Single<List<Pull>> {
        return dataSourceRemote.getAll(repo)
            .flattenAsObservable { it }
            .flatMap{
                userDataSource.add(it.assignee)
                    .andThen(dataSourceLocal.addPull(it))
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