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
    suspend fun getRepoPulls(repo: Repo): Flow<Pull> {
        dataSourceRemote.getAll(repo)
            .onEach { pull ->
                userDataSource.add(pull.assignee)
                dataSourceLocal.addPull(pull)
                pull.assignees.filterNotNull()
                    .forEach {
                        userDataSource.add(it)
                        dataSourceLocal.addPullUserCrossRef(pull, it)
                    }
            }.collect()
        return dataSourceLocal.getRepoPulls(repo)
    }
}