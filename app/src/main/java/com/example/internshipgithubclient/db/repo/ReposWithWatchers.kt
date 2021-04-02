package com.example.internshipgithubclient.db.repo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.internshipgithubclient.db.user.UserRoomEntity

data class ReposWithWatchers(
    @Embedded val repoRoomEntity: RepoRoomEntity,
    @Relation(
        parentColumn = "repoId",
        entityColumn = "userId",
        associateBy = Junction(ReposUsersCrossRef::class)
    )
    val userRoomEntities:List<UserRoomEntity>
)