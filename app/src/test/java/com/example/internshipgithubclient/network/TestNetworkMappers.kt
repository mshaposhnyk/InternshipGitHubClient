package com.example.internshipgithubclient.network

import com.example.core.domain.Pull
import com.example.internshipgithubclient.network.pull.HeadNetworkEntity
import com.example.internshipgithubclient.network.pull.MilestoneNetworkEntity
import com.example.internshipgithubclient.network.pull.PullNetworkEntity
import com.example.internshipgithubclient.network.repo.IssueNetworkEntity
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity

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