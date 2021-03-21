package com.example.core.domain

data class Repo(
    val description: String,
    val forks: Int,
    val forksCount: Int,
    val fullName: String,
    val name: String,
    val owner: User,
    val stargazersCount: Int,
    val watchersCount: Int
)