package com.example.internshipgithubclient.db

import com.example.core.domain.Issue
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.internshipgithubclient.db.issue.IssueRoomEntity
import com.example.internshipgithubclient.db.issue.IssueWithAssignees
import com.example.internshipgithubclient.db.pull.PullRoomEntity
import com.example.internshipgithubclient.db.pull.PullWithAssignees
import com.example.internshipgithubclient.db.repo.RepoRoomEntity
import com.example.internshipgithubclient.db.user.UserRoomEntity
import com.example.internshipgithubclient.network.toIssueState


fun UserRoomEntity.toDomain(): User {
    return User(
        this.userId,
        this.avatarUrl,
        this.bio,
        this.company,
        this.email,
        this.followers,
        this.following,
        this.gistsUrl,
        this.location,
        this.login,
        this.name,
        this.publicGists,
        this.publicRepos
    )
}

fun RepoRoomEntity.toDomain(user: User): Repo {
    return Repo(
        this.repoId,
        this.url,
        this.description,
        this.forks,
        this.forksCount,
        this.fullName,
        this.name,
        user,
        this.stargazersCount,
        this.openIssuesCount,
        this.watchersCount
    )
}

fun IssueWithAssignees.toDomain(): Issue {
    return Issue(
        this.issueRoomEntity.repoUrl,
        this.issueRoomEntity.issueId,
        this.issueRoomEntity.number,
        this.issueRoomEntity.state.toIssueState(),
        this.issueRoomEntity.title,
        this.issueRoomEntity.body,
        this.issueRoomEntity.user.toDomain(),
        this.issueRoomEntity.assignee?.toDomain(),
        this.userRoomEntities.map { it.toDomain() },
        this.issueRoomEntity.commentsCount
    )
}

fun PullWithAssignees.toDomain(): Pull {
    return Pull(
        this.pullRoomEntity.pullId,
        this.pullRoomEntity.number,
        this.pullRoomEntity.repoUrl,
        this.pullRoomEntity.state.toIssueState(),
        this.pullRoomEntity.title,
        this.pullRoomEntity.body,
        this.pullRoomEntity.user.toDomain(),
        this.pullRoomEntity.assignee?.toDomain(),
        this.userRoomEntities.map {
            it.toDomain()
        }
    )
}

fun User.fromDomain(isCurrentUser: Boolean): UserRoomEntity {
    return UserRoomEntity(
        this.id,
        isCurrentUser,
        this.avatarUrl,
        this.bio,
        this.company,
        this.email,
        this.followers,
        this.following,
        this.gistsUrl,
        this.location,
        this.login,
        this.name,
        this.publicGists,
        this.publicRepos
    )
}

fun Repo.fromDomain(): RepoRoomEntity {
    return RepoRoomEntity(
        this.id,
        this.url,
        this.description,
        this.forks,
        this.forksCount,
        this.fullName,
        this.name,
        this.owner.id,
        this.stargazersCount,
        this.openIssuesCount,
        this.watchersCount
    )
}

fun Issue.fromDomain(): IssueRoomEntity {
    return IssueRoomEntity(
        this.id,
        this.repoUrl,
        this.number,
        this.state.toString(),
        this.title,
        this.body,
        this.user.id,
        this.assignee?.id,
        this.commentsCount
    )
}

fun Pull.fromDomain(): PullRoomEntity {
    return PullRoomEntity(
        this.id,
        this.repoUrl,
        this.number,
        this.state.toString(),
        this.title,
        this.body,
        this.user.id,
        this.assignee?.id
    )
}

