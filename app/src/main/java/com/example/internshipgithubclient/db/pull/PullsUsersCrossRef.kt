package com.example.internshipgithubclient.db.pull

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.internshipgithubclient.db.repo.RepoRoomEntity
import com.example.internshipgithubclient.db.user.UserRoomEntity

@Entity(
    primaryKeys = ["pullId", "userId"],
    foreignKeys = [ForeignKey(
        entity = PullRoomEntity::class,
        parentColumns = ["pullId"],
        childColumns = ["pullId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UserRoomEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("pullId"), Index("userId")]
)
data class PullsUsersCrossRef(
    val pullId: Int,
    val userId: Int
)