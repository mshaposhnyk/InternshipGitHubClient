package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

interface LocalUserDataSource {
    suspend fun deleteUser(user: User)

    suspend fun addAuthorized(user: User)

    suspend fun add(user: User?)

    suspend fun getById(id: Int): User

    suspend fun getAuthorized(): User
}