package com.example.internshipgithubclient.db.user

import com.example.core.data.LocalUserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomUserDataSource(private val userDao: UserDao) : LocalUserDataSource {
    override suspend fun deleteUser(user: User) {
        var isCurrentUser = false
        val authorizedUser = userDao.getAuthorizedUser()
        if (user.id == authorizedUser.userId) isCurrentUser = true
        return userDao.deleteUser(user.fromDomain(isCurrentUser))
    }

    override suspend fun addAuthorized(user: User) {
        return userDao.addUser(user.fromDomain(true))
    }


    override suspend fun add(user: User?) {
        if (user != null) {
            val authorizedUser = userDao.getAuthorizedUser()
            userDao.addUser(user.fromDomain(user.id == authorizedUser.userId))
        }
    }

    override suspend fun getById(id: Int): User {
        return userDao.getUserById(id).toDomain()
    }

    override suspend fun getAuthorized(): User {
        return userDao.getAuthorizedUser().toDomain()
    }
}