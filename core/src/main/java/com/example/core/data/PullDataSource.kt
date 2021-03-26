package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import io.reactivex.Single

interface PullDataSource {
    fun getAll(repo: Repo): Single<List<Pull>>
}