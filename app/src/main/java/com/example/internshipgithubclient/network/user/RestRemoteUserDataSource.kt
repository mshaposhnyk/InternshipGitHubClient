package com.example.internshipgithubclient.network.user

import com.example.core.data.RemoteUserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestRemoteUserDataSource(val userApiService: UserApiService) : RemoteUserDataSource {
    override fun get(): Single<User> {
        return userApiService.getAuthenticatedUser()
            .subscribeOn(Schedulers.io())
            .flatMap { Single.just(it.toDomain()) }
    }
}