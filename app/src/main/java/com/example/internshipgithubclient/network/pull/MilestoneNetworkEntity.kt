package com.example.internshipgithubclient.network.pull

import android.os.Parcelable
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MilestoneNetworkEntity(
    @SerializedName("id")
    val id:Int,
    @SerializedName("node_id")
    val node_id:String,
    @SerializedName("number")
    val number:Int,
    @SerializedName("state")
    val state:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("creator")
    val creator:UserNetworkEntity,
    @SerializedName("open_issues")
    val open_issues:Int,
    @SerializedName("closed_issues")
    val closed_issues:Int
):Parcelable
