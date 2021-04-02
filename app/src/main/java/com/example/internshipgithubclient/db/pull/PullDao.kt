package com.example.internshipgithubclient.db.pull

import androidx.room.*
import com.example.internshipgithubclient.db.issue.IssueWithAssignees
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PullDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPull(pullRoomEntity: PullRoomEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPullsAssigneeCrossRef(pullsWithAssignees: PullsUsersCrossRef): Completable

    @Update
    fun updatePull(pullRoomEntity: PullRoomEntity)

    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    fun getAllPulls(repoUrl: String): Single<List<PullRoomEntity>>

    @Transaction
    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    fun getPullsWithAssignees(repoUrl: String): Single<List<PullWithAssignees>>

    @Transaction
    @Query("SELECT * from pull WHERE pullId = :pullId")
    fun getPullWithAssignees(pullId: Int): Single<PullWithAssignees>

    @Query("SELECT * from pull WHERE pullId=:id")
    fun getPullById(id: Int): Single<PullRoomEntity>

    @Delete
    fun deletePull(pullRoomEntity: PullRoomEntity): Completable
}