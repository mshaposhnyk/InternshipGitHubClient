package com.example.internshipgithubclient.db.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    tableName = "repo", foreignKeys = [ForeignKey(
        entity = UserRoomEntity::class,
        parentColumns = ["userId"],
        childColumns = ["ownerId"],
        onDelete = CASCADE
    )]
)
data class RepoRoomEntity(
    @PrimaryKey val repoId: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "forks") val forks: Int,
    @ColumnInfo(name = "forksCount") val forksCount: Int,
    @ColumnInfo(name = "fullName") val fullName: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(index = true) val ownerId: Int,
    @ColumnInfo(name = "stargazersCount") val stargazersCount: Int,
    @ColumnInfo(name = "openIssuesCount") val openIssuesCount: Int,
    @ColumnInfo(name = "watchersCount") val watchersCount: Int
)