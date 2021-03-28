package com.example.internshipgithubclient.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserRoomEntity(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "isCurrentUser") var isCurrentUser:Boolean,
    @ColumnInfo(name = "avatarUrl") val avatarUrl: String,
    @ColumnInfo(name = "bio") val bio: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "followers") val followers: Int,
    @ColumnInfo(name = "following") val following: Int,
    @ColumnInfo(name = "gistsUrl") val gistsUrl: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "publicGists") val publicGists: Int,
    @ColumnInfo(name = "publicRepos") val publicRepos: Int
)