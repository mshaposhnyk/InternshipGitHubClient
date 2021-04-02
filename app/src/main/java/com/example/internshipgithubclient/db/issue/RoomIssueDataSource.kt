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

class RoomIssueDataSource(private val issueDao: IssueDao, private val userDao: UserDao) :
    LocalIssueDataSource {
    override fun addIssue(issue: Issue): Completable {
        return issueDao.addIssue(issue.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    override fun addIssueAssigneeCrossRef(issue: Issue, assignee: User): Completable {
        return issueDao.addIssueAssigneeCrossRef(IssuesUsersCrossRef(issue.id, assignee.id))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }


    override fun deleteIssue(issue: Issue): Completable {
        return issueDao.deleteIssue(issue.fromDomain())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    override fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return issueDao.getIssuesWithAssignees(repo.url)
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .flatMap { issue ->
                userDao.getUserById(issue.issueRoomEntity.userId)
                    .toObservable()
                    .flatMap { user ->
                        issue.issueRoomEntity.user = user
                        Observable.just(issue)
                    }
            }
            .flatMap { issue ->
                if (issue.issueRoomEntity.assigneeId != null) {
                    userDao.getUserById(issue.issueRoomEntity.assigneeId)
                        .toObservable()
                        .flatMap {
                            issue.issueRoomEntity.assignee = it
                            Observable.just(issue)
                        }
                } else {
                    Observable.just(issue)
                }
            }
            .map{
                it.toDomain()
            }
            .toList()/*
            .flatMap { issuesWithAssignees ->
                Single.just(issuesWithAssignees.map { issueWithAssignee ->
                    val issueRoomEntity = issueWithAssignee.issueRoomEntity
                    var userRoomEntity: UserRoomEntity? = null
                    var assigneeRoomEntity: UserRoomEntity? = null
                    userDao.getUserById(issueRoomEntity.userId)
                        .subscribe(
                            {
                                if (it != null)
                                    userRoomEntity = it
                            }, {

                            })
                    issueRoomEntity.assigneeId?.let {
                        userDao.getUserById(it).subscribe({ user ->
                            if (user != null)
                                assigneeRoomEntity = user

                        }, {

                        })
                    }
                    issueRoomEntity.toDomain(
                        issueWithAssignee,
                        userRoomEntity!!,
                        assigneeRoomEntity
                    )
                })
            }*/
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

}
