package com.example.core.domain

import java.io.Serializable

class Pull(
    val id: Int,
    val number: Int,
    val repoUrl: String,
    val state: IssueState,
    val title: String,
    val body: String,
    val user: User,
    val assignee: User?,
    val assignees: List<User?>
) : Serializable