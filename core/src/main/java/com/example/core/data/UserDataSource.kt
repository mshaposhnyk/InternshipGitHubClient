package com.example.core.data

import com.example.core.domain.User

interface UserDataSource {
    suspend fun get(): User
}