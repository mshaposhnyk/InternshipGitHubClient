package com.example.internshipgithubclient.db.pull

import androidx.room.*

@Dao
interface PullDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPull(pullRoomEntity: PullRoomEntity)

    @Update
    fun updatePull(pullRoomEntity: PullRoomEntity)

    @Query("SELECT * from pull WHERE repo_url = :repoUrl")
    fun getAllPulls(repoUrl: String): List<PullRoomEntity>

    @Query("SELECT * from pull WHERE pullId=:id")
    fun getPullById(id: Int): PullRoomEntity

    @Delete
    fun deletePull(pullRoomEntity: PullRoomEntity)
}