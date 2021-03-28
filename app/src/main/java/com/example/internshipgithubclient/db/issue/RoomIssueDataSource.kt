package com.example.internshipgithubclient.db.issue

import com.example.core.data.LocalIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import io.reactivex.Single

class RoomIssueDataSource(private val issueDao: IssueDao, private val userDao: UserDao) :
    LocalIssueDataSource {
    override fun addIssue(issue: Issue) {
        val authorizedUser = userDao.getAuthorizedUser()
        issue.assignee?.let {
            if (it.id == authorizedUser.userId) {
                userDao.addUser(it.fromDomain(true))
            } else {
                userDao.addUser(it.fromDomain(false))
            }
        }
        issueDao.addIssue(issue.fromDomain())
        if (issue.assignees.isNotEmpty()) {
            issue.assignees.forEach {
                if (it.id == authorizedUser.userId) {
                    userDao.addUser(it.fromDomain(true))
                } else {
                    userDao.addUser(it.fromDomain(false))
                }
                issueDao.addIssueAssigneeCrossRef(IssuesUsersCrossRef(issue.id,it.id))
            }

        }
    }

    override fun deleteIssue(issue: Issue) {
        issueDao.deleteIssue(issue.fromDomain())
    }

    override fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return Single.just(issueDao.getIssuesWithAssignees(repo.url))
            .flatMap { issuesWithAssignees -> Single.just(issuesWithAssignees.map { issueWithAssignee->
                val issueRoomEntity = issueWithAssignee.issueRoomEntity
                val userRoomEntity = userDao.getUserById(issueRoomEntity.userId)
                val assigneeRoomEntity:UserRoomEntity? = issueRoomEntity.assigneeId?.let { userDao.getUserById(it) }
                issueRoomEntity.toDomain(issueWithAssignee,userRoomEntity,assigneeRoomEntity)
            }) }
    }
}