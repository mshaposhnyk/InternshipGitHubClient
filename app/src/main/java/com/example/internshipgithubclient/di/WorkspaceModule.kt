package com.example.internshipgithubclient.di

import com.example.internshipgithubclient.ui.workspace.repoDetails.RepoDetailsFragment
import com.example.internshipgithubclient.ui.workspace.repoIssues.RepoIssuesFragment
import com.example.internshipgithubclient.ui.workspace.repoList.RepoListFragment
import com.example.internshipgithubclient.ui.workspace.repoPulls.RepoPullsFragment
import com.example.internshipgithubclient.ui.workspace.repoWatchers.RepoWatchersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface WorkspaceModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepoPullsModule::class,PullsViewModelModule::class])
    fun contributePullsFragment(): RepoPullsFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RepoIssuesModule::class,IssuesViewModelModule::class])
    fun contributeIssueFragment(): RepoIssuesFragment

    @ContributesAndroidInjector(modules = [RepoListViewModelModule::class])
    fun contributeRepoListFragmentInjector(): RepoListFragment

    @ContributesAndroidInjector(modules=[RepoDetailsViewModelModule::class])
    fun contributeRepoDetailsFragment(): RepoDetailsFragment

    @ContributesAndroidInjector(modules=[RepoWatchersViewModelModule::class])
    fun contributeWatchersFragment(): RepoWatchersFragment


}