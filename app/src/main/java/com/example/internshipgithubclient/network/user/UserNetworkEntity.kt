package com.example.internshipgithubclient.network.user

import com.google.gson.annotations.SerializedName

data class UserNetworkEntity (
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int
)