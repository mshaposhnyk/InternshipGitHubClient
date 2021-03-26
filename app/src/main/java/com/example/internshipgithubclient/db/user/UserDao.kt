package com.example.internshipgithubclient.db.user

import androidx.room.*

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userRoomEntity: UserRoomEntity)

    @Query("SELECT * FROM user WHERE isCurrentUser = 1")
    fun getAuthorizedUser(): UserRoomEntity

    @Update
    fun updateUser(userRoomEntity: UserRoomEntity)

    @Delete
    fun deleteUser(roomEntity: UserRoomEntity)
}