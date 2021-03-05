package com.example.internshipgithubclient.login.network.repo


import com.google.gson.annotations.SerializedName

data class LicenseNetworkEntity(
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("spdx_id")
    val spdxId: String,
    @SerializedName("url")
    val url: String
)