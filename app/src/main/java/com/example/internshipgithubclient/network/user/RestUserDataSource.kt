package com.example.internshipgithubclient.network.user

import com.example.core.data.UserDataSource
import com.example.core.domain.User

class RestUserDataSource : UserDataSource {
    override suspend fun get(): User {
        TODO("Not yet implemented")
    }
}