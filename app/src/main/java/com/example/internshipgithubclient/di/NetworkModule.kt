package com.example.internshipgithubclient.di

import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.user.UserApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class NetworkModule {
    @Provides
    fun provideUserApi(retrofitClient: Retrofit): UserApiService = retrofitClient.create()

    @Provides
    fun provideRepoApi(retrofitClient: Retrofit): RepoApiService = retrofitClient.create()
}