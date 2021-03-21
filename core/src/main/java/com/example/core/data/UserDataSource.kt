package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

interface UserDataSource {
    suspend fun get(): Single<User>
}