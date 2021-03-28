package com.example.internshipgithubclient.db.user

import com.example.core.data.LocalUserDataSource
import com.example.core.data.RemoteUserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import io.reactivex.Single

class RoomUserDataSource(private val userDao: UserDao) : LocalUserDataSource {
    override fun addAuthorized(user: User) {
        userDao.addUser(user.fromDomain(true))
    }

    override fun deleteUser(user: User) {
        val userRoom = userDao.getAuthorizedUser()

        if (userRoom.userId == user.id) {
            userDao.deleteUser(user.fromDomain(true))
        } else {
            userDao.deleteUser(user.fromDomain(false))
        }
    }

    override fun add(user: User) {
        userDao.addUser(user.fromDomain(false))
    }

    override fun get(): Single<User> {
        return Single.just(userDao.getAuthorizedUser().toDomain())
    }
}