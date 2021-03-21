package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo

interface PullDataSource {
    suspend fun getAll(repo: Repo): List<Pull>
}