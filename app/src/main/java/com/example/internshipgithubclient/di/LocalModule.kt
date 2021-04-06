package com.example.internshipgithubclient.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.internshipgithubclient.db.InternGitHubClientDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Provides
    @Singleton
    fun provideDataBase(app: Application) =
        Room.databaseBuilder(app, InternGitHubClientDatabase::class.java, "github")
            .build()
}

@Module
class DaoModule {
    @Provides
    @Singleton
    fun provideUserDao(database: InternGitHubClientDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideRepoDao(database: InternGitHubClientDatabase) = database.repoDao()

    @Provides
    @Singleton
    fun provideIssueDao(database: InternGitHubClientDatabase) = database.issueDao()

    @Provides
    @Singleton
    fun providePullDao(database: InternGitHubClientDatabase) = database.pullDao()
}