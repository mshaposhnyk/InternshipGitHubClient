package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import kotlinx.coroutines.flow.Flow

interface RemotePullDataSource {
    suspend fun getAll(repo: Repo): Flow<Pull>
}