package com.example.internshipgithubclient.db.pull

import androidx.room.*
import com.example.internshipgithubclient.db.issue.IssueWithAssignees
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PullDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPull(pullRoomEntity: PullRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPullsAssigneeCrossRef(pullsWithAssignees: PullsUsersCrossRef)

    @Update
    suspend fun updatePull(pullRoomEntity: PullRoomEntity)

    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    suspend fun getAllPulls(repoUrl: String): List<PullRoomEntity>

    @Transaction
    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    suspend fun getPullsWithAssignees(repoUrl: String): List<PullWithAssignees>

    @Transaction
    @Query("SELECT * from pull WHERE pullId = :pullId")
    suspend fun getPullWithAssignees(pullId: Int): PullWithAssignees

    @Query("SELECT * from pull WHERE pullId=:id")
    suspend fun getPullById(id: Int): PullRoomEntity

    @Delete
    suspend fun deletePull(pullRoomEntity: PullRoomEntity)
}