package com.example.internshipgithubclient.db.repo

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepo(repo: RepoRoomEntity): Completable

    @Update
    fun updateRepo(repo: RepoRoomEntity): Completable

    @Query("SELECT * FROM repo where ownerId=:userId")
    fun getAllUserRepos(userId: Int): Single<List<RepoRoomEntity>>

    @Query("SELECT * FROM repo where ownerId=:userId and name=:repoName")
    fun getDedicatedRepo(userId: Int, repoName: String): Single<RepoRoomEntity>

    @Transaction
    @Query("SELECT * from repo WHERE repoId = :repoId")
    fun getRepoWithWatchers(repoId: Int): Single<ReposWithWatchers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepoWatcher(crossRef: ReposUsersCrossRef): Completable

    @Delete
    fun deleteRepo(repo: RepoRoomEntity): Completable
}