package com.example.internshipgithubclient.db.pull

import com.example.core.data.LocalPullDataSource
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import io.reactivex.Single

class RoomPullDataSource(private val pullDao: PullDao,private val userDao: UserDao) : LocalPullDataSource{
    override fun addPull(pull: Pull) {
        val authorizedUser = userDao.getAuthorizedUser()
        pull.assignee?.let {
            if (it.id == authorizedUser.userId) {
                userDao.addUser(it.fromDomain(true))
            } else {
                userDao.addUser(it.fromDomain(false))
            }
        }
        pullDao.addPull(pull.fromDomain())
        if (pull.assignees.isNotEmpty()) {
            pull.assignees.forEach {
                if (it.id == authorizedUser.userId) {
                    userDao.addUser(it.fromDomain(true))
                } else {
                    userDao.addUser(it.fromDomain(false))
                }
                pullDao.addPullsAssigneeCrossRef(PullsUsersCrossRef(pull.id,it.id))
            }

        }
    }

    override fun deletePull(pull: Pull) {
        pullDao.deletePull(pull.fromDomain())
    }

    override fun getAll(repo: Repo): Single<List<Pull>> {
        return Single.just(pullDao.getPullsWithAssignees(repo.url))
            .flatMap { issuesWithAssignees -> Single.just(issuesWithAssignees.map { issueWithAssignee->
                val pullRoomEntity = issueWithAssignee.pullRoomEntity
                val userRoomEntity = userDao.getUserById(pullRoomEntity.userId)
                val assigneeRoomEntity: UserRoomEntity? = pullRoomEntity.assigneeId?.let { userDao.getUserById(it) }
                pullRoomEntity.toDomain(issueWithAssignee,userRoomEntity,assigneeRoomEntity)
            }) }
    }
}