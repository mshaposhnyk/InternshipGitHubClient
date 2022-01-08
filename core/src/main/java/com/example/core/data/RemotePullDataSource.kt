package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface RemotePullDataSource {
    fun getAll(repo: Repo): Single<List<Pull>>
}