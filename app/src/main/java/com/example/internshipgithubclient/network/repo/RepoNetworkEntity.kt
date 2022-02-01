package com.example.internshipgithubclient.network.repo

import android.os.Parcelable
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoNetworkEntity(
    @SerializedName("url")
    val url: String,
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("forks")
    val forks: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("has_downloads")
    val hasDownloads: Boolean,
    @SerializedName("has_issues")
    val hasIssues: Boolean,
    @SerializedName("has_pages")
    val hasPages: Boolean,
    @SerializedName("has_projects")
    val hasProjects: Boolean,
    @SerializedName("has_wiki")
    val hasWiki: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_template")
    val isTemplate: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("owner")
    val owner: UserNetworkEntity,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("pushed_at")
    val pushedAt: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("topics")
    val topics: List<String>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("watchers_count")
    val watchersCount: Int
) : Parcelable