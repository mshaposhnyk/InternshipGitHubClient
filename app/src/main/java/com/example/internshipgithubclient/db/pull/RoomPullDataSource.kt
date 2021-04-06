package com.example.internshipgithubclient.db.pull

import com.example.core.data.LocalPullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class RoomPullDataSource(private val pullDao: PullDao, private val userDao: UserDao) :
    LocalPullDataSource {
    override suspend fun addPull(pull: Pull) {
        return pullDao.addPull(pull.fromDomain())
    }

    override suspend fun deletePull(pull: Pull) {
        return pullDao.deletePull(pull.fromDomain())
    }

    override suspend fun addPullUserCrossRef(pull: Pull, user: User) {
        return pullDao.addPullsAssigneeCrossRef(PullsUsersCrossRef(pull.id,user.id))
    }

    override suspend fun getRepoPulls(repo: Repo): Flow<Pull> {
        return pullDao.getPullsWithAssignees(repo.url).asFlow()
            .map { pull->
                pull.pullRoomEntity.user = userDao.getUserById(pull.pullRoomEntity.userId)
                pull.pullRoomEntity.assigneeId?.let {
                    userDao.getUserById(pull.pullRoomEntity.userId)
                }
                pull.toDomain()
            }
    }

}
