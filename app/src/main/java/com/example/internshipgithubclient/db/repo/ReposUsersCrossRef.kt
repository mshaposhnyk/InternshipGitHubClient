package com.example.internshipgithubclient.db.repo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    primaryKeys = ["repoId", "userId"],
    foreignKeys = [ForeignKey(
        entity = RepoRoomEntity::class,
        parentColumns = ["repoId"],
        childColumns = ["repoId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UserRoomEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("repoId"), Index("userId")]
)
data class ReposUsersCrossRef(
    val repoId: Int,
    val userId: Int
)