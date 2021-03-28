package com.example.internshipgithubclient.db.pull

import androidx.room.*
import com.example.internshipgithubclient.db.issue.IssueWithAssignees

@Dao
interface PullDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPull(pullRoomEntity: PullRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPullsAssigneeCrossRef(pullsWithAssignees: PullsUsersCrossRef)

    @Update
    fun updatePull(pullRoomEntity: PullRoomEntity)

    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    fun getAllPulls(repoUrl: String): List<PullRoomEntity>

    @Transaction
    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    fun getPullsWithAssignees(repoUrl: String):List<PullWithAssignees>

    @Transaction
    @Query("SELECT * from pull WHERE pullId = :pullId")
    fun getPullWithAssignees(pullId: Int): PullWithAssignees

    @Query("SELECT * from pull WHERE pullId=:id")
    fun getPullById(id: Int): PullRoomEntity

    @Delete
    fun deletePull(pullRoomEntity: PullRoomEntity)
}