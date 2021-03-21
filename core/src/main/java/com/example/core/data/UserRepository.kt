package com.example.core.data

import com.example.core.domain.User

class UserRepository(private val dataSource: UserDataSource) {
    suspend fun getUser(): User = dataSource.get()
}