package com.example.internshipgithubclient.login.network.repo


import com.google.gson.annotations.SerializedName

data class PermissionsNetworkEntity(
    @SerializedName("admin")
    val admin: Boolean,
    @SerializedName("pull")
    val pull: Boolean,
    @SerializedName("push")
    val push: Boolean
)