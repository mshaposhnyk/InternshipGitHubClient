package com.example.internshipgithubclient.di

import androidx.lifecycle.ViewModel
import com.example.internshipgithubclient.ui.workspace.repoPulls.ClosedPullsFragment
import com.example.internshipgithubclient.ui.workspace.repoPulls.OpenPullsFragment
import com.example.internshipgithubclient.ui.workspace.repoPulls.PullsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RepoPullsModule {
    @ContributesAndroidInjector
    abstract fun contributeOpenPullsFragment(): OpenPullsFragment

    @ContributesAndroidInjector
    abstract fun contributeClosedPullsFragment(): ClosedPullsFragment
}

@Module
abstract class PullsViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(PullsViewModel::class)
    internal abstract fun bindPullsViewModel(viewModel: PullsViewModel): ViewModel
}