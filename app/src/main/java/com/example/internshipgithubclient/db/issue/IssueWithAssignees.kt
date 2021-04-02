package com.example.internshipgithubclient.db.issue

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.internshipgithubclient.db.user.UserRoomEntity

data class IssueWithAssignees(
    @Embedded val issueRoomEntity: IssueRoomEntity,
    @Relation(
        parentColumn = "issueId",
        entityColumn = "userId",
        associateBy = Junction(IssuesUsersCrossRef::class)
    )
    val userRoomEntities:List<UserRoomEntity>
)