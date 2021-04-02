package com.example.internshipgithubclient.db.issue

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.internshipgithubclient.db.pull.PullRoomEntity
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    primaryKeys = ["issueId", "userId"],
    foreignKeys = [ForeignKey(
        entity = IssueRoomEntity::class,
        parentColumns = ["issueId"],
        childColumns = ["issueId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UserRoomEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("issueId"),Index("userId")]
)
data class IssuesUsersCrossRef(
    val issueId: Int,
    val userId: Int
)