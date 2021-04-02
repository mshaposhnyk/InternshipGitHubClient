package com.example.internshipgithubclient.di

import com.example.core.data.*
import com.example.core.interactors.*
import com.example.internshipgithubclient.db.issue.IssueDao
import com.example.internshipgithubclient.db.issue.RoomIssueDataSource
import com.example.internshipgithubclient.db.pull.PullDao
import com.example.internshipgithubclient.db.pull.RoomPullDataSource
import com.example.internshipgithubclient.db.repo.RepoDao
import com.example.internshipgithubclient.db.repo.RoomRepoDataSource
import com.example.internshipgithubclient.db.user.RoomUserDataSource
import com.example.internshipgithubclient.db.user.UserDao
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RestRemoteIssueDataSource
import com.example.internshipgithubclient.network.pull.RestRemotePullDataSource
import com.example.internshipgithubclient.network.repo.RestRemoteRepoDataSource
import com.example.internshipgithubclient.network.user.RestRemoteUserDataSource
import com.example.internshipgithubclient.network.user.UserApiService
import dagger.Module
import dagger.Provides

@Module
class LocalDataSourcesModule {
    @Provides
    fun provideUserDataSource(userDao: UserDao): LocalUserDataSource = RoomUserDataSource(userDao)

    @Provides
    fun provideRepoDataSource(repoDao: RepoDao, userDao: UserDao): LocalRepoDataSource =
        RoomRepoDataSource(repoDao, userDao)

    @Provides
    fun provideIssueDataSource(issueDao: IssueDao, userDao: UserDao): LocalIssueDataSource =
        RoomIssueDataSource(issueDao, userDao)

    @Provides
    fun providePullDataSource(pullDao: PullDao, userDao: UserDao): LocalPullDataSource =
        RoomPullDataSource(pullDao, userDao)
}

@Module
class RemoteDataSourcesModule {
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
    fun provideRepoRepository(
        remoteRepoDataSource: RemoteRepoDataSource,
        localRepoDataSource: LocalRepoDataSource
    ) = RepoRepository(remoteRepoDataSource, localRepoDataSource)

    @Provides
    fun provideUserRepository(
        remoteUserDataSource: RemoteUserDataSource,
        localUserDataSource: LocalUserDataSource
    ) = UserRepository(remoteUserDataSource, localUserDataSource)

    @Provides
    fun providesPullRepository(
        remotePullDataSource: RemotePullDataSource,
        localPullDataSource: LocalPullDataSource,
        localUserDataSource: LocalUserDataSource
    ) = PullRepository(remotePullDataSource, localPullDataSource, localUserDataSource)

    @Provides
    fun providesIssueRepository(
        remoteIssueDataSource: RemoteIssueDataSource,
        localIssueDataSource: LocalIssueDataSource,
        localUserDataSource: LocalUserDataSource
    ) = IssueRepository(remoteIssueDataSource, localIssueDataSource, localUserDataSource)
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