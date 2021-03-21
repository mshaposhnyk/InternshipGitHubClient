package com.example.internshipgithubclient.di

import android.app.Application
import com.example.internshipgithubclient.InternGitHubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        RetrofitModule::class,
        NetworkModule::class,
        ViewModelFactoryModule::class]
)
interface AppComponent : AndroidInjector<InternGitHubApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}