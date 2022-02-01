package com.example.core.fakeDataSources

import com.example.core.createTestIssue
import com.example.core.data.LocalIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

class FakeLocalIssueDataSource: LocalIssueDataSource {
    private val listOfIssues = ArrayList<Issue>()
    val issueAssignee = HashMap<Issue,User>()


    override fun addIssue(issue: Issue): Completable {
        listOfIssues.add(issue)
        return Completable.complete()
    }

    override fun addIssueAssigneeCrossRef(issue: Issue, assignee: User): Completable {
        issueAssignee[issue] = assignee
        return Completable.complete()
    }

    override fun deleteIssue(issue: Issue): Completable {
        listOfIssues.remove(issue)
        return Completable.complete()
    }

    override fun getRepoIssues(repo: Repo): Single<List<Issue>> {
        return Single.just(listOf(createTestIssue(repo.owner.id), createTestIssue(repo.owner.id)))
    }
}