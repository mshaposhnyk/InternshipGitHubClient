package com.example.internshipgithubclient.network

import com.example.core.domain.*
import com.example.internshipgithubclient.network.pullRequest.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity

fun UserNetworkEntity.toDomain(): User {
    return User(
        this.avatarUrl,
        this.bio ?: "",
        this.company ?: "",
        this.email ?: "",
        this.followers,
        this.following,
        this.gistsUrl,
        this.location ?: "",
        this.login,
        this.name ?: "",
        this.publicGists,
        this.publicRepos
    )
}

fun List<UserNetworkEntity>.toDomain():List<User>{
    return this.map {
        it.toDomain()
    }
}

fun RepoNetworkEntity.toDomain(): Repo {
    return Repo(
        this.description ?: "",
        this.forks,
        this.forksCount,
        this.fullName,
        this.name,
        this.owner.toDomain(),
        this.stargazersCount,
        this.openIssuesCount,
        this.watchersCount
    )
}

fun String.toIssueState():IssueState{
    return when(this){
        "open" -> IssueState.OPEN
        "closed" -> IssueState.CLOSED
        else -> IssueState.ALL
    }
}

fun IssueNetworkEntity.toDomain():Issue{
    return Issue(
        this.number,
        this.state.toIssueState(),
        this.title,
        this.body,
        this.user.toDomain(),
        this.assignee.toDomain(),
        this.assignees.toDomain(),
        this.commentsCount
    )
}

fun PullNetworkEntity.toDomain():Pull{
    var assignee: User? = null
    if (this.assignee != null)
        assignee = this.assignee.toDomain()
    return Pull(
        this.number,
        this.state.toIssueState(),
        this.title,
        this.body,
        this.user.toDomain(),
        assignee,
        this.assignees.toDomain()
    )
}