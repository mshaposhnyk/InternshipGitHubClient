package com.example.core.domain

class Pull(
    val number: Int,
    val state: IssueState,
    val title: String,
    val body: String,
    val user: User,
    val assignee: User,
    val assignees: List<User>,
    val commentsCount: Int
)