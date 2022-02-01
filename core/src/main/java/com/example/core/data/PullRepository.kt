package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Observable
import io.reactivex.Single
import com.example.core.domain.Result

open class PullRepository(
    private val dataSourceRemote: RemotePullDataSource,
    private val dataSourceLocal: LocalPullDataSource,
    private val userDataSource: LocalUserDataSource
) {
    open fun getRepoPulls(repo: Repo): Single<Result<List<Pull>>> {
        val remotePulls = dataSourceRemote.getAll(repo)
            .flattenAsObservable { it }
            .flatMap{ pull ->
                userDataSource.add(pull.assignee)
                    .andThen(dataSourceLocal.addPull(pull))
                    .andThen(Observable.fromIterable(pull.assignees.filterNotNull())
                        .flatMap {
                            userDataSource.add(it)
                                .andThen(Observable.just(it))
                        }
                        .flatMapCompletable { user ->
                            dataSourceLocal.addPullUserCrossRef(pull,user)
                        })
                    .andThen(Observable.just(pull))
            }.toList()

        val localPulls = dataSourceLocal.getRepoPulls(repo)
        return Single.concat(remotePulls, localPulls)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }
}