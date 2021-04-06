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
    override fun deleteUser(user: User): Completable {
        var isCurrentUser = false
        userDao.getAuthorizedUser().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { it ->
                if (it.userId == user.id) isCurrentUser = true
            }.dispose()
        return userDao.deleteUser(user.fromDomain(isCurrentUser))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addAuthorized(user: User): Completable {
        return userDao.addUser(user.fromDomain(true))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getById(id: Int): Single<User> {
        return userDao.getUserById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toDomain() }
    }

    override fun getAuthorized(): Single<User> {
        return userDao.getAuthorizedUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toDomain() }
    }
}