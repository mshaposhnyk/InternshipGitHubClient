package com.example.internshipgithubclient.di

import com.example.core.data.*
import com.example.core.interactors.*
import com.example.internshipgithubclient.network.repo.RepoApiService
import com.example.internshipgithubclient.network.repo.RestIssueDataSource
import com.example.internshipgithubclient.network.repo.RestPullDataSource
import com.example.internshipgithubclient.network.repo.RestRepoDataSource
import com.example.internshipgithubclient.network.user.RestUserDataSource
import com.example.internshipgithubclient.network.user.UserApiService
import dagger.Module
import dagger.Provides

@Module
class DataSourcesModule {
    @Provides
    fun provideUserDataSource(userApiService: UserApiService): UserDataSource =
        RestUserDataSource(userApiService)

    @Provides
    fun provideRepoDataSource(repoApiService: RepoApiService): RepoDataSource =
        RestRepoDataSource(repoApiService)

    @Provides
    fun provideIssueDataSource(repoApiService: RepoApiService): IssueDataSource =
        RestIssueDataSource(repoApiService)

    @Provides
    fun providePullDataSource(repoApiService: RepoApiService): PullDataSource =
        RestPullDataSource(repoApiService)
}

@Module
class RepositoriesModule {
    @Provides
    fun provideRepoRepository(repoDataSource: RepoDataSource) = RepoRepository(repoDataSource)

    @Provides
    fun provideUserRepository(userDataSource: UserDataSource) = UserRepository(userDataSource)

    @Provides
    fun providesPullRepository(pullDataSource: PullDataSource) = PullRepository(pullDataSource)

    @Provides
    fun providesIssueRepository(issueDataSource: IssueDataSource) = IssueRepository(issueDataSource)
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