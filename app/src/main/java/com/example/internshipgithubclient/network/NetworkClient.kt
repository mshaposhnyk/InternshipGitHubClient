package com.example.internshipgithubclient.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val HEADER_AUTHORIZATION = "Authorization"
const val HEADER_APPLICATION = "Accept"
const val HEADER_APPLICATION_CONTENT = """" application/vnd.github.v3+json"""
const val API_URL = "https://api.github.com"

//Singleton. Returns retrofit client
class NetworkClient {
    companion object {
        private var INSTANCE: Retrofit? = null

        fun getInstance(token: String): Retrofit = INSTANCE ?: buildClient(token)

        private fun buildClient(token: String): Retrofit {
            //constructing authorization header content
            val tokenHeader = """ token $token"""
            //adding headers with interceptor
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                                            .addInterceptor(Interceptor { chain ->
                                                val originalRequest: Request = chain.request()
                                                val newRequest: Request = originalRequest.newBuilder()
                                                         .addHeader(HEADER_AUTHORIZATION, tokenHeader)
                                                        .addHeader(HEADER_APPLICATION, HEADER_APPLICATION_CONTENT)
                                                        .build()
                                                chain.proceed(newRequest)
                                            }).build()
            INSTANCE = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return INSTANCE as Retrofit
        }
    }
}