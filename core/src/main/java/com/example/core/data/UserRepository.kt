package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single
import com.example.core.domain.Result


open class UserRepository(
    private val dataSourceRemote: RemoteUserDataSource,
    private val dataSourceLocal: LocalUserDataSource
) {
    open fun getUser(): Single<Result<User>> {
        val remoteUser = dataSourceRemote.get()
            .flatMap {
                dataSourceLocal.addAuthorized(it)
                    .andThen(Single.just(it))
            }
        val localUser = dataSourceLocal.getAuthorized()
        return Single.concat(remoteUser,localUser)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }
}