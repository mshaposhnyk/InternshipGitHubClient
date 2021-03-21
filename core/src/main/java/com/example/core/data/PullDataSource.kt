package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Single

interface PullDataSource {
    suspend fun getAll(repo: Repo): Single<List<Pull>>
}