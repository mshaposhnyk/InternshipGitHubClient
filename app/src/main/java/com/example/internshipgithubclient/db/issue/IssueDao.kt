package com.example.internshipgithubclient.db.issue

import androidx.room.*

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIssue(issueRoomEntity: IssueRoomEntity)

    @Update
    fun updateIssue(issueRoomEntity: IssueRoomEntity)

    @Transaction
    @Query("SELECT * from issue WHERE repo_url = :repoUrl")
    fun getIssuesWithAssignees(repoUrl: String):List<IssueWithAssignees>

    @Transaction
    @Query("SELECT * from issue WHERE issueId=:id")
    fun getIssueById(id: Int): IssueRoomEntity

    @Delete
    fun deleteIssue(issueRoomEntity: IssueRoomEntity)
}