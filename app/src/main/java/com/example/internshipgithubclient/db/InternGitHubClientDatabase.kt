package com.example.internshipgithubclient.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.internshipgithubclient.db.issue.IssueDao
import com.example.internshipgithubclient.db.issue.IssueRoomEntity
import com.example.internshipgithubclient.db.issue.IssuesUsersCrossRef
import com.example.internshipgithubclient.db.pull.PullDao
import com.example.internshipgithubclient.db.pull.PullRoomEntity
import com.example.internshipgithubclient.db.pull.PullsUsersCrossRef
import com.example.internshipgithubclient.db.repo.RepoDao
import com.example.internshipgithubclient.db.repo.RepoRoomEntity
import com.example.internshipgithubclient.db.repo.ReposUsersCrossRef
import com.example.internshipgithubclient.db.repo.ReposWithWatchers
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.db.user.UserRoomEntity
import com.example.internshipgithubclient.di.ActivityScope
import javax.inject.Singleton

@Database(
    entities = [UserRoomEntity::class,
        RepoRoomEntity::class,
        PullRoomEntity::class,
        IssueRoomEntity::class,
        ReposUsersCrossRef::class,
        IssuesUsersCrossRef::class,
        PullsUsersCrossRef::class],
    version = 1,
    exportSchema = false
)

abstract class InternGitHubClientDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao

    abstract fun pullDao(): PullDao

    abstract fun issueDao(): IssueDao
}