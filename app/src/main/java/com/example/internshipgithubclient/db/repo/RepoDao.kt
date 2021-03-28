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

    @Query("SELECT * FROM repo where ownerId=:userId and name=:repoName")
    fun getDedicatedRepo(userId: Int, repoName: String): RepoRoomEntity

    @Transaction
    @Query("SELECT * from repo WHERE repoId = :repoId")
    fun getRepoWithWatchers(repoId: Int): ReposWithWatchers

    @Delete
    fun deleteRepo(repo: RepoRoomEntity)
}