package com.example.internshipgithubclient.di

import com.example.internshipgithubclient.network.*
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun providesInterceptor(authStateHelper: AuthStateHelper): Interceptor {
        val tokenHeader = """ token ${authStateHelper.currentAuthState.accessToken}"""
        return Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val newRequest: Request = originalRequest.newBuilder()
                .addHeader(HEADER_AUTHORIZATION, tokenHeader)
                .addHeader(HEADER_APPLICATION, HEADER_APPLICATION_CONTENT)
                .build()
            chain.proceed(newRequest)

        }
    }

    @Singleton
    @Provides
    fun providesHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}