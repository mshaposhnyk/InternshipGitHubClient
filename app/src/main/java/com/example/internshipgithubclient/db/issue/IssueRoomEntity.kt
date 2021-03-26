package com.example.internshipgithubclient.db.issue

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    tableName = "issue", foreignKeys = [ForeignKey(
        entity = UserRoomEntity::class,
        parentColumns = ["userId"],
        childColumns = ["assigneeId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
            entity = UserRoomEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class IssueRoomEntity(
    @PrimaryKey val issueId: Int,
    @ColumnInfo(name = "repo_url") val repoUrl: String,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(index = true) val userId: Int,
    @ColumnInfo(index = true) val assigneeId: Int,
    @ColumnInfo(name = "commentsCount") val commentsCount: Int
)