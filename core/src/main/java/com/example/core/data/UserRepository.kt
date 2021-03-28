package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

class UserRepository(private val dataSourceRemote: RemoteUserDataSource) {
    fun getUser(): Single<User> = dataSourceRemote.get()
}