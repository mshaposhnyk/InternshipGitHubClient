package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow


interface LocalPullDataSource {
    suspend fun addPull(pull: Pull)
    suspend fun deletePull(pull: Pull)
    suspend fun addPullUserCrossRef(pull: Pull, user: User)
    suspend fun getRepoPulls(repo: Repo): Flow<Pull>
}