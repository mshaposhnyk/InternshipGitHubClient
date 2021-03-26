package com.example.internshipgithubclient.db.pull

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["pullId", "userId"],
    indices = [Index("pullId"), Index("userId")]
)
data class PullsUsersCrossRef(
    val pullId: Int,
    val userId: Int
)