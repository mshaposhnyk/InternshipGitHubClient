package com.example.internshipgithubclient.network.user

import retrofit2.http.GET
import retrofit2.http.Path

const val USER_HEADER = "Accept: application/vnd.github.v3+json"

interface UserApiService {

    //Returns currently authenticated user
    @GET("/user")
    fun getAuthenticatedUser(): UserNetworkEntity

    //Returns public info about specific user
    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): UserNetworkEntity


    //Lists the people following the authenticated user
    @GET("/user/followers")
    fun getAutheticatedUserFollowers(): List<UserNetworkEntity>
}