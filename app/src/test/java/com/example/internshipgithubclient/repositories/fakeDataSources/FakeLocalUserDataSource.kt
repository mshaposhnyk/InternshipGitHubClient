package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.LocalUserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.createTestUser
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserRoomEntity

class FakeLocalUserDataSource : LocalUserDataSource {
    private val listOfUsers = ArrayList<UserRoomEntity>()

    override suspend fun deleteUser(user: User) {
        var isCurrentUser = false
        val authorizedUser = getAuthorized().fromDomain(true)
        if (user.id == authorizedUser.userId) isCurrentUser = true
        listOfUsers.remove(user.fromDomain(isCurrentUser))
    }

    override suspend fun addAuthorized(user: User) {
        listOfUsers.add(user.fromDomain(true))
    }

    override suspend fun add(user: User?) {
        user?.let {
            listOfUsers.add(it.fromDomain(false))
        }
    }

    override suspend fun getById(id: Int): User {
        var user = createTestUser(-1)
        listOfUsers.forEach {
            if(it.userId == id) {
                user = it.toDomain()
                return@forEach
            }
        }
        return user
    }

    override suspend fun getAuthorized(): User {
        var user = createTestUser(-1)
        listOfUsers.forEach {
            if(it.isCurrentUser) {
                user = it.toDomain()
                return@forEach
            }
        }
        return user
    }
}