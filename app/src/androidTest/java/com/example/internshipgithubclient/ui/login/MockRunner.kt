package com.example.internshipgithubclient.ui.login

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.runner.AndroidJUnitRunner
import com.example.internshipgithubclient.di.*
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

class MockRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestInternGitHubApp::class.java.name, context)
    }
}


@Module
class TestDataStoreModule {
    @Singleton
    @Provides
    fun providePreferenceDataStore(app: Application): DataStore<Preferences> =
        PreferenceDataStoreFactory.create (
            produceFile = {
                app.preferencesDataStoreFile("testAuthState")
            }
        ).apply {
            runBlocking {
                edit {
                    it.clear()
                }
            }
        }

    @Singleton
    @Provides
    fun providePreferenceKey(): Preferences.Key<String> = stringPreferencesKey("state")
}

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        RetrofitModule::class,
        NetworkModule::class,
        DbModule::class,
        DaoModule::class,
        ViewModelFactoryModule::class,
        TestDataStoreModule::class]
)
interface TestAppComponent : AndroidInjector<TestInternGitHubApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): TestAppComponent
    }
}

class TestInternGitHubApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerTestAppComponent.factory().create(this).inject(this)
    }

}