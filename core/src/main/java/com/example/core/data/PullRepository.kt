package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Single

class PullRepository (private val dataSourceRemote: RemotePullDataSource) {
    fun getRepoPulls(repo: Repo): Single<List<Pull>> = dataSourceRemote.getAll(repo)
}