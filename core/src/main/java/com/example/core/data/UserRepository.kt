package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val dataSourceRemote: RemoteUserDataSource,
    private val dataSourceLocal: LocalUserDataSource
) {
    suspend fun getUser(): User {
        val user = dataSourceRemote.get()
        dataSourceLocal.addAuthorized(user)
        return dataSourceLocal.getAuthorized()
    }
}