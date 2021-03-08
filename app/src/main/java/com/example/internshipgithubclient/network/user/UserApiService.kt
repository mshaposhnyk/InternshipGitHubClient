package com.example.internshipgithubclient.network.user

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface UserApiService {

    //Returns currently authenticated user
    @GET("/user")
    fun getAuthenticatedUser(): Call<UserNetworkEntity>

    //Returns public info about specific user
    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<UserNetworkEntity>


    //Lists the people following the authenticated user
    @GET("/user/followers")
    fun getAutheticatedUserFollowers(): Call<List<UserNetworkEntity>>
}