package com.example.internshipgithubclient.db.issue

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    primaryKeys = ["issueId", "userId"],
    indices = [Index("issueId"),Index("userId")]
)
data class IssuesUsersCrossRef(
    val issueId: Int,
    val userId: Int
)