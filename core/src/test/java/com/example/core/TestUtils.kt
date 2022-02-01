package com.example.core

import com.example.core.domain.*
import java.util.ArrayList

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

fun createTestRepo(repoId: Int, userId: Int, repoName: String = "") = Repo(
    id = repoId,
    url = "",
    description = "",
    forks = 0,
    forksCount = 0,
    fullName = "",
    name = repoName,
    createTestUser(userId),
    stargazersCount = 0,
    openIssuesCount = 0,
    watchersCount = 0
)

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

fun createTestIssue(userId: Int) = Issue(
    id = 1,
    number = 2,
    repoUrl = "",
    state = IssueState.OPEN,
    title = "",
    body = "",
    user = createTestUser(userId),
    assignee = null,
    assignees = ArrayList<User>(),
    commentsCount = 3
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