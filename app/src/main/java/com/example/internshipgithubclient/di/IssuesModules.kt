package com.example.internshipgithubclient.di

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.ui.workspace.repoIssues.ClosedIssuesFragment
import com.example.internshipgithubclient.ui.workspace.repoIssues.IssuesViewModel
import com.example.internshipgithubclient.ui.workspace.repoIssues.OpenIssuesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RepoIssuesModule {
    @ContributesAndroidInjector
    abstract fun contributeOpenIssuesFragment(): OpenIssuesFragment

    @ContributesAndroidInjector
    abstract fun contributeClosedIssuesFragment(): ClosedIssuesFragment
}

@Module
abstract class IssuesViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(IssuesViewModel::class)
    internal abstract fun bindPullsViewModel(viewModel: IssuesViewModel): ViewModel
}