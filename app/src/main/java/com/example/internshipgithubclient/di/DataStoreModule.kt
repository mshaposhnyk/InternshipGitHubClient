package com.example.internshipgithubclient.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataStoreModule {
    @Singleton
    @Provides
    fun providePreferenceDataStore(app: Application):DataStore<Preferences> =
        PreferenceDataStoreFactory.create (
            produceFile = {
                app.preferencesDataStoreFile("authState")
            }
        )

    @Singleton
    @Provides
    fun providePreferenceKey():Preferences.Key<String> = stringPreferencesKey("state")
}