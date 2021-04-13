package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.LocalIssueDataSource
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.createTestIssue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeLocalIssueDataSource: LocalIssueDataSource {
    val listOfIssues = ArrayList<Issue>()
    val issueAssignee = HashMap<Issue,User>()


    override suspend fun addIssue(issue: Issue) {
        listOfIssues.add(issue)
    }

    override suspend fun addIssueAssigneeCrossRef(issue: Issue, assignee: User) {
        issueAssignee[issue] = assignee
    }

    override suspend fun deleteIssue(issue: Issue) {
        listOfIssues.remove(issue)
    }

    override suspend fun getRepoIssues(repo: Repo): Flow<Issue> {
        return listOf(createTestIssue(repo.owner.id), createTestIssue(repo.owner.id)).asFlow()
    }
}