package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single

interface RemoteUserDataSource {
    suspend fun get(): User
}