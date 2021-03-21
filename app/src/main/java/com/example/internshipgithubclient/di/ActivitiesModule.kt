package com.example.internshipgithubclient.di

import com.example.internshipgithubclient.ui.login.LoginActivity
import com.example.internshipgithubclient.ui.workspace.UserWorkSpaceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [WorkspaceModule::class, ViewModelModule::class])
    fun provideWorkSpaceActivity(): UserWorkSpaceActivity
}