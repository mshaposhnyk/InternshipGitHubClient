package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

interface LocalUserDataSource {
    fun deleteUser(user: User): Completable

    fun addAuthorized(user: User): Completable

    fun add(user: User?): Completable

    fun getById(id: Int): Single<User>

    fun getAuthorized(): Single<User>
}