package com.example.internshipgithubclient.db.issue

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIssue(issueRoomEntity: IssueRoomEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIssueAssigneeCrossRef(issuesWithAssignees: IssuesUsersCrossRef): Completable

    @Update
    fun updateIssue(issueRoomEntity: IssueRoomEntity): Completable

    @Transaction
    @Query("SELECT * from issue WHERE repo_url = :repoUrl")
    fun getIssuesWithAssignees(repoUrl: String): Single<List<IssueWithAssignees>>

    @Transaction
    @Query("SELECT * FROM issue WHERE issueId=:issueId")
    fun getIssueWithAssignees(issueId: Int): Single<IssueWithAssignees>

    @Transaction
    @Query("SELECT * from issue WHERE issueId=:id")
    fun getIssueById(id: Int): Single<IssueRoomEntity>

    @Delete
    fun deleteIssue(issueRoomEntity: IssueRoomEntity): Completable
}