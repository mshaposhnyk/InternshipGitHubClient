package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun getUser(): Single<User> = dataSource.get()
}