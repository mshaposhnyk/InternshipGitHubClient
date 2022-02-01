package com.example.core.data

import com.example.core.domain.Issue
import com.example.core.domain.Repo
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.Single
import com.example.core.domain.*


open class IssueRepository(
    private val dataSourceRemote: RemoteIssueDataSource,
    private val dataSourceLocal: LocalIssueDataSource,
    private val userDataSource: LocalUserDataSource
) {
   open fun getRepoIssues(repo: Repo): Single<Result<List<Issue>>> {
        val remoteIssues = dataSourceRemote.getRepoIssues(repo)
            .flattenAsObservable { it }
            .flatMap { issue ->
                userDataSource.add(issue.assignee)
                    .andThen(dataSourceLocal.addIssue(issue))
                    .andThen(Observable.fromIterable(issue.assignees.filterNotNull())
                        .flatMapCompletable {
                            userDataSource.add(it)
                                .andThen(dataSourceLocal.addIssueAssigneeCrossRef(issue, it))
                        })
                    .andThen(just(issue))
            }.toList()

        val localIssues = dataSourceLocal.getRepoIssues(repo)
        return Single.concat(remoteIssues, localIssues)
            .lastOrError()
            .map {
                Result.Success(it)
            }
    }
}