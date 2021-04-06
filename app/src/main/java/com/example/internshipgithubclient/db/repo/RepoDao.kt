package com.example.internshipgithubclient.db.repo

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: RepoRoomEntity)

    @Update
    suspend fun updateRepo(repo: RepoRoomEntity)

    @Query("SELECT * FROM repo where ownerId=:userId")
    suspend fun getAllUserRepos(userId: Int): List<RepoRoomEntity>

    @Query("SELECT * FROM repo where ownerId=:userId and name=:repoName")
    suspend fun getDedicatedRepo(userId: Int, repoName: String): RepoRoomEntity

    @Transaction
    @Query("SELECT * from repo WHERE repoId = :repoId")
    suspend fun getRepoWithWatchers(repoId: Int): ReposWithWatchers

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepoWatcher(crossRef: ReposUsersCrossRef)

    @Delete
    suspend fun deleteRepo(repo: RepoRoomEntity)
}