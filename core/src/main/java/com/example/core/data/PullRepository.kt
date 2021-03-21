package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo

class PullRepository (private val dataSource: PullDataSource) {
    suspend fun getRepoPulls(repo:Repo):List<Pull> = dataSource.getAll(repo)
}