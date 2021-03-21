package com.example.internshipgithubclient.di

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.ui.login.LoginViewModel
import com.example.internshipgithubclient.ui.workspace.repoDetails.RepoDetailsViewModel
import com.example.internshipgithubclient.ui.workspace.repoList.RepoListViewModel
import com.example.internshipgithubclient.ui.workspace.repoWatchers.RepoWatchersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}