package com.example.internshipgithubclient.db.user

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userRoomEntity: UserRoomEntity): Completable

    @Query("SELECT * FROM user WHERE isCurrentUser = 1")
    fun getAuthorizedUser(): Single<UserRoomEntity>

    @Query("SELECT * FROM user WHERE userId=:id")
    fun getUserById(id: Int): Single<UserRoomEntity>

    @Update
    fun updateUser(userRoomEntity: UserRoomEntity): Completable

    @Delete
    fun deleteUser(roomEntity: UserRoomEntity): Completable
}