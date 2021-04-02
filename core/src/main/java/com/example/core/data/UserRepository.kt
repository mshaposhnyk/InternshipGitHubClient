package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

class UserRepository(
    private val dataSourceRemote: RemoteUserDataSource,
    private val dataSourceLocal: LocalUserDataSource
) {
    fun getUser(): Single<User> = dataSourceRemote.get()
        .doOnSuccess {
            dataSourceLocal.addAuthorized(it).subscribe()
        }
        .onErrorResumeNext {
            dataSourceLocal.getAuthorized()
        }
}