package com.example.internshipgithubclient.network.user

import com.example.core.data.UserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.network.toDomain
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RestUserDataSource(val userApiService: UserApiService) : UserDataSource {
    override fun get(): Single<User> {
        return userApiService.getAuthenticatedUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { Single.just(it.toDomain()) }
    }
}