package com.example.core.fakeDataSources

import com.example.core.createTestUser
import com.example.core.data.LocalUserDataSource
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

class FakeLocalUserDataSource : LocalUserDataSource {
    private val listOfUsers = ArrayList<User>()

    override fun deleteUser(user: User): Completable {
        listOfUsers.remove(user)
        return Completable.complete()
    }

    override fun addAuthorized(user: User): Completable {
        listOfUsers.add(user)
        return Completable.complete()
    }

    override fun add(user: User?): Completable {
        user?.let {
            listOfUsers.add(it)
        }
        return Completable.complete()
    }

    override fun getById(id: Int): Single<User> {
        var user = createTestUser(-1)
        listOfUsers.forEach {
            if(it.id == id) {
                user = it
                return@forEach
            }
        }
        return Single.just(user)
    }

    override fun getAuthorized(): Single<User> {
        return Single.create {
            it.onSuccess((listOfUsers.first()))
        }
    }
}