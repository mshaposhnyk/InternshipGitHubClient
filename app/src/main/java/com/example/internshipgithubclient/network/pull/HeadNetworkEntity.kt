package com.example.internshipgithubclient.network.pull

import android.os.Parcelable
import com.example.internshipgithubclient.network.repo.RepoNetworkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeadNetworkEntity(
    @SerializedName("label")
    val label: String,
    @SerializedName("repo")
    val repo: RepoNetworkEntity
) : Parcelable
