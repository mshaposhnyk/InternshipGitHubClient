package com.example.internshipgithubclient.di

import com.example.core.data.*
import com.example.core.interactors.*
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RestRemoteIssueDataSource
import com.example.internshipgithubclient.network.repo.RestRemotePullDataSource
import com.example.internshipgithubclient.network.repo.RestRemoteRepoDataSource
import com.example.internshipgithubclient.network.user.RestRemoteUserDataSource
import com.example.internshipgithubclient.network.user.UserApiService
import dagger.Module
import dagger.Provides

@Module
class DataSourcesModule {
    @Provides
    fun provideUserDataSource(userApiService: UserApiService): RemoteUserDataSource =
        RestRemoteUserDataSource(userApiService)

    @Provides
    fun provideRepoDataSource(repoApiService: RepoApiService): RemoteRepoDataSource =
        RestRemoteRepoDataSource(repoApiService)

    @Provides
    fun provideIssueDataSource(repoApiService: RepoApiService): RemoteIssueDataSource =
        RestRemoteIssueDataSource(repoApiService)

    @Provides
    fun providePullDataSource(repoApiService: RepoApiService): RemotePullDataSource =
        RestRemotePullDataSource(repoApiService)
}

@Module
class RepositoriesModule {
    @Provides
    fun provideRepoRepository(remoteRepoDataSource: RemoteRepoDataSource) = RepoRepository(remoteRepoDataSource)

    @Provides
    fun provideUserRepository(remoteUserDataSource: RemoteUserDataSource) = UserRepository(remoteUserDataSource)

    @Provides
    fun providesPullRepository(remotePullDataSource: RemotePullDataSource) = PullRepository(remotePullDataSource)

    @Provides
    fun providesIssueRepository(remoteIssueDataSource: RemoteIssueDataSource) = IssueRepository(remoteIssueDataSource)
}

@Module
class InteractionsModule {
    @Provides
    fun provideGetAllUserReposInteraction(repoRepository: RepoRepository) =
        GetAllUserRepos(repoRepository)

    @Provides
    fun provideGetDedicatedRepoInteraction(repoRepository: RepoRepository) =
        GetDedicatedRepo(repoRepository)

    @Provides
    fun provideGetRepoIssuesInteraction(issueRepository: IssueRepository) =
        GetRepoIssues(issueRepository)

    @Provides
    fun provideGetRepoPullsInteraction(pullRepository: PullRepository) =
        GetRepoPulls(pullRepository)

    @Provides
    fun provideGetUserInteraction(userRepository: UserRepository) = GetUser(userRepository)

    @Provides
    fun provideGetWatchersRepo(repoRepository: RepoRepository) = GetWatchersRepo(repoRepository)
}