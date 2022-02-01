package com.example.core.data

import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow


interface LocalPullDataSource {
    fun addPull(pull: Pull): Completable
    fun deletePull(pull: Pull): Completable
    fun addPullUserCrossRef(pull: Pull, user: User): Completable
    fun getRepoPulls(repo: Repo): Single<List<Pull>>
}