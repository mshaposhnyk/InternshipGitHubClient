package com.example.internshipgithubclient.db.user

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userRoomEntity: UserRoomEntity)

    @Query("SELECT * FROM user WHERE isCurrentUser = 1")
    suspend fun getAuthorizedUser(): UserRoomEntity

    @Query("SELECT * FROM user WHERE userId=:id")
    suspend fun getUserById(id: Int): UserRoomEntity

    @Update
    suspend fun updateUser(userRoomEntity: UserRoomEntity)

    @Delete
    suspend fun deleteUser(roomEntity: UserRoomEntity)
}