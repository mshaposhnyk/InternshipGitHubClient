package com.example.core.domain

data class User(
    val bio: String,
    val company: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val gistsUrl: String,
    val location: String,
    val login: String,
    val name: String,
    val publicGists: Int,
    val publicRepos: Int
)