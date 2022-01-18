package com.example.internshipgithubclient

import com.example.core.domain.*
import com.example.internshipgithubclient.network.pull.HeadNetworkEntity
import com.example.internshipgithubclient.network.pull.MilestoneNetworkEntity
import com.example.internshipgithubclient.network.pull.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import java.util.ArrayList

fun createTestPull(userId: Int) = Pull(
    id = 1,
    number = 2,
    repoUrl = "",
    state = IssueState.OPEN,
    title = "",
    body = "",
    user = createTestUser(userId),
    assignee = null,
    assignees = ArrayList<User>()
)

fun createTestPull(user:User) = Pull(
    id = 1,
    number = 2,
    repoUrl = "",
    state = IssueState.OPEN,
    title = "",
    body = "",
    user = user,
    assignee = null,
    assignees = ArrayList<User>()
)

fun createTestIssue(user:User, repo:Repo,assignee:User) = Issue(
    id = 1,
    number = 2,
    repoUrl = "",
    state = IssueState.OPEN,
    title = "",
    body = "",
    user = user,
    assignee = assignee,
    assignees = listOf(assignee),
    commentsCount = 3
)

fun createTestPull(user:User, repo:Repo,assignee:User) = Pull(
    id = 1,
    number = 2,
    repoUrl = repo.url,
    state = IssueState.OPEN,
    title = "",
    body = "",
    user = user,
    assignee = assignee,
    assignees = listOf(assignee)
)

fun createTestRemotePull(): PullNetworkEntity =
    PullNetworkEntity(
        0,
        createTestRemoteHead(),
        0,
        "",
        "",
        "",
        createTestRemoteUser(),
        createTestRemoteMilestone(),
        createTestRemoteUser(1),
        listOf(createTestRemoteUser(1))
    )

fun createTestRemoteMilestone(): MilestoneNetworkEntity =
    MilestoneNetworkEntity(
        0,
        "",
        0,
        "",
        "",
        "",
        createTestRemoteUser(),
        0,
        0
    )

fun createTestRemoteHead(): HeadNetworkEntity =
    HeadNetworkEntity(
        "",
        createTestRemoteRepo()
    )

fun createTestRemoteUser(id:Int = 0): UserNetworkEntity =
    UserNetworkEntity(
        "",
        "",
        "",
        "",
        0,
        0,
        "",
        id,
        "",
        "",
        "",
        "",
        0,
        0
    )

fun createTestRemoteIssue(): IssueNetworkEntity =
    IssueNetworkEntity(
        "",
        0,
        0,
        "",
        "",
        "",
        createTestRemoteUser(),
        createTestRemoteUser(1),
        listOf(createTestRemoteUser(1)),
        0
    )

fun createTestRemoteRepo(): RepoNetworkEntity =
    RepoNetworkEntity(
        "",
        false,
        "",
        "",
        "",
        false,
        false,
        0,
        0,
        "",
        false,
        false,
        false,
        false,
        false,
        0,
        false,
        "",
        "",
        0,
        createTestRemoteUser(),
        false,
        "",
        0,
        0,
        listOf(),
        "",
        "",
        0
    )

fun createTestUser(id: Int) = User(
    id = id,
    avatarUrl = "",
    bio = "",
    company = "",
    email = "",
    followers = 1,
    following = 2,
    gistsUrl = "",
    location = "",
    login = "",
    name = "",
    publicGists = 3,
    publicRepos = 4
)

fun createTestRepo(userId : Int) = Repo(
    id = 1,
    url = "",
    description = "",
    forks = 0,
    forksCount = 0,
    fullName = "",
    name = "",
    createTestUser(userId),
    stargazersCount = 0,
    openIssuesCount = 0,
    watchersCount = 0
)

fun createTestRepo(repoId:Int, user: User) = Repo(
    id = repoId,
    url = "",
    description = "",
    forks = 0,
    forksCount = 0,
    fullName = "",
    name = "",
    user,
    stargazersCount = 0,
    openIssuesCount = 0,
    watchersCount = 0
)