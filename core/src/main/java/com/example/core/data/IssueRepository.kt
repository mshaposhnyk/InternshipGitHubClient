package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.Single

class IssueRepository(
    private val dataSourceRemote: RemoteIssueDataSource,
    private val dataSourceLocal: LocalIssueDataSource,
    private val userDataSource: LocalUserDataSource
) {
    fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return dataSourceRemote.getRepoIssues(repo)
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .flatMap{
                userDataSource.add(it.assignee)
                    .andThen(just(it))
            }
            .flatMap {
                dataSourceLocal.addIssue(it)
                    .andThen(just(it))
            }
            .flatMapCompletable { issue ->
                Observable.fromIterable(issue.assignees.filterNotNull())
                    .flatMap {
                        userDataSource.add(it)
                            .andThen(just(it))
                    }
                    .flatMapCompletable { user ->
                        dataSourceLocal.addIssueAssigneeCrossRef(issue,user)
                    }
            }
            .andThen(dataSourceLocal.getRepoIssues(repo))
            .onErrorResumeNext (dataSourceLocal.getRepoIssues(repo))
    }
}