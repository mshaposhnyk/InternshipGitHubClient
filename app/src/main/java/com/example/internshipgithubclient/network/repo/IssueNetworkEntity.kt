package com.example.internshipgithubclient.network.repo

import android.os.Parcelable
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssueNetworkEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("number")
    val number:Int,
    @SerializedName("state")
    val state:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("body")
    val body:String,
    @SerializedName("user")
    val user:UserNetworkEntity,
    @SerializedName("assignee")
    val assignee:UserNetworkEntity,
    @SerializedName("assignees")
    val assignees: List<UserNetworkEntity>,
    @SerializedName("comments")
    val commentsCount: Int
): Parcelable
