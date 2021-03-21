package com.example.internshipgithubclient.di

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.ui.workspace.repoDetails.RepoDetailsViewModel
import com.example.internshipgithubclient.ui.workspace.repoList.RepoListViewModel
import com.example.internshipgithubclient.ui.workspace.repoWatchers.RepoWatchersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RepoListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    internal abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel
}

@Module
abstract class RepoWatchersViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoWatchersViewModel::class)
    internal abstract fun bindRepoWatchersViewModel(viewModel: RepoWatchersViewModel): ViewModel
}

@Module
abstract class RepoDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailsViewModel::class)
    internal abstract fun bindRepoDetailsViewModel(viewModel: RepoDetailsViewModel): ViewModel
}
