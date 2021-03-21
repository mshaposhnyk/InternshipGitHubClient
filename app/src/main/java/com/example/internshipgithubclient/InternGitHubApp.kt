package com.example.internshipgithubclient

import android.app.Application
import com.example.internshipgithubclient.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class InternGitHubApp : Application(),HasAndroidInjector{

   @Inject
   lateinit var androidInjector: DispatchingAndroidInjector<Any>

   override fun androidInjector(): AndroidInjector<Any> {
      return androidInjector
   }

   override fun onCreate() {
       super.onCreate()
       DaggerAppComponent.factory().create(this).inject(this)

   }

}