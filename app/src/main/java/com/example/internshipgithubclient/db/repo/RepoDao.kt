package com.example.internshipgithubclient.db.repo

import androidx.room.*

@Dao
interface RepoDao {
    @Insert
    fun addRepo(repo: RepoRoomEntity)

    @Update
    fun updateRepo(repo: RepoRoomEntity)

    @Query("SELECT * FROM repo where ownerId=:userId")
    fun getAllUserRepos(userId: Int): List<RepoRoomEntity>

    @Delete
    fun deleteRepo(repo: RepoRoomEntity)
}