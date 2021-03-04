package com.example.internshipgithubclient.login.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_LINK = "https://api.github.com"

object NetworkClient {
    val retrofit = Retrofit.Builder()
        .baseUrl(API_LINK)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}