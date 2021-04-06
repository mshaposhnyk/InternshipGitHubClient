package com.example.internshipgithubclient.db.issue

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIssue(issueRoomEntity: IssueRoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addIssueAssigneeCrossRef(issuesWithAssignees: IssuesUsersCrossRef)

    @Update
    suspend fun updateIssue(issueRoomEntity: IssueRoomEntity)

    @Transaction
    @Query("SELECT * from issue WHERE repo_url = :repoUrl")
    suspend fun getIssuesWithAssignees(repoUrl: String): List<IssueWithAssignees>

    @Transaction
    @Query("SELECT * FROM issue WHERE issueId=:issueId")
    suspend fun getIssueWithAssignees(issueId: Int): IssueWithAssignees

    @Transaction
    @Query("SELECT * from issue WHERE issueId=:id")
    suspend fun getIssueById(id: Int): IssueRoomEntity

    @Delete
    suspend fun deleteIssue(issueRoomEntity: IssueRoomEntity)
}