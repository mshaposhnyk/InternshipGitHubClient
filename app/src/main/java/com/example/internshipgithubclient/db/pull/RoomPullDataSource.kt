package com.example.internshipgithubclient.db.pull

import com.example.core.data.LocalPullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class RoomPullDataSource(private val pullDao: PullDao, private val userDao: UserDao) :
    LocalPullDataSource {
    override fun addPull(pull: Pull): Completable {
        return pullDao.addPull(pull.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deletePull(pull: Pull): Completable {
        return pullDao.deletePull(pull.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addPullUserCrossRef(pull: Pull, user: User): Completable {
        return pullDao.addPullsAssigneeCrossRef(PullsUsersCrossRef(pull.id,user.id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRepoPulls(repo: Repo): Single<List<Pull>> {
        return pullDao.getPullsWithAssignees(repo.url)
            .flattenAsObservable { it }
            .flatMap { pull->
                userDao.getUserById(pull.pullRoomEntity.userId)
                    .toObservable()
                    .flatMap { user ->
                        pull.pullRoomEntity.user = user
                        Observable.just(pull)
                    }
            }
            .flatMap { pull ->
                if (pull.pullRoomEntity.assigneeId != null) {
                    userDao.getUserById(pull.pullRoomEntity.assigneeId)
                        .toObservable()
                        .flatMap {
                            pull.pullRoomEntity.assignee = it
                            Observable.just(pull)
                        }
                } else {
                    Observable.just(pull)
                }
            }
            .map{
                it.toDomain()
            }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
