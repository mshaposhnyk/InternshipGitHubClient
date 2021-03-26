package com.example.internshipgithubclient.db.pull

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.internshipgithubclient.db.user.UserRoomEntity

data class PullWithAssignees(
    @Embedded val issueRoomEntity: PullRoomEntity,
    @Relation(
        parentColumn = "pullId",
        entityColumn = "userId",
        associateBy = Junction(PullsUsersCrossRef::class)
    )
    val userRoomEntities:List<UserRoomEntity>
)