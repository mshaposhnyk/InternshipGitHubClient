package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

interface LocalUserDataSource {
    fun addAuthorized(user: User)

    fun deleteUser(user: User)

    fun add(user: User)

    fun get(): Single<User>
}