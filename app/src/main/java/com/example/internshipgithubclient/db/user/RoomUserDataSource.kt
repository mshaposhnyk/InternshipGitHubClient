package com.example.internshipgithubclient.db.user

import com.example.core.data.LocalUserDataSource
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.ui.createDummyUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomUserDataSource(private val userDao: UserDao) : LocalUserDataSource {
    override fun deleteUser(user: User): Completable {
        var isCurrentUser = false
        userDao.getAuthorizedUser().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { it ->
                if (it.userId == user.id) isCurrentUser = true
            }.dispose()
        return userDao.deleteUser(user.fromDomain(isCurrentUser))
    }

    override fun addAuthorized(user: User): Completable {
        return userDao.addUser(user.fromDomain(true))
    }

    override fun add(user: User?): Completable {
        val completable = if (user == null)
            Completable.complete()
        else
            getAuthorized()
                .flatMap {
                    Single.just(it.id == user.id)
                }
                .flatMapCompletable {
                    userDao.addUser(user.fromDomain(it))
                }

        return completable
    }

    override fun getById(id: Int): Single<User> {
        return userDao.getUserById(id)
            .map { it.toDomain() }
    }

    override fun getAuthorized(): Single<User> {
        return userDao.getAuthorizedUser()
            .doOnError {
                it
                val t = 0
            }
            .onErrorReturnItem(createDummyUser().fromDomain(true))
            .map {
                it.toDomain()
            }
    }
}