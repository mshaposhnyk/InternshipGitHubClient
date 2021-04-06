package com.example.internshipgithubclient.db.issue

import com.example.core.data.LocalIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.fromDomain
import com.example.internshipgithubclient.db.toDomain
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class RoomIssueDataSource(private val issueDao: IssueDao, private val userDao: UserDao) :
    LocalIssueDataSource {
    override suspend fun addIssue(issue: Issue) {
        return issueDao.addIssue(issue.fromDomain())
    }

    override suspend fun addIssueAssigneeCrossRef(issue: Issue, assignee: User) {
        return issueDao.addIssueAssigneeCrossRef(IssuesUsersCrossRef(issue.id, assignee.id))
    }


    override suspend fun deleteIssue(issue: Issue) {
        return issueDao.deleteIssue(issue.fromDomain())
    }

    override suspend fun getRepoIssues(repo: Repo): Flow<Issue> {
        return issueDao.getIssuesWithAssignees(repo.url).asFlow()
            .map { issue->
                issue.issueRoomEntity.user = userDao.getUserById(issue.issueRoomEntity.userId)
                issue.issueRoomEntity.assigneeId?.let {
                    userDao.getUserById(issue.issueRoomEntity.userId)
                }
                issue.toDomain()
            }
    }

}
