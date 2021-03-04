package com.example.internshipgithubclient.login.network.user

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


const val USER_HEADER = "Accept: application/vnd.github.v3+json"

interface UserServiceAPI {
    @Headers(USER_HEADER)
    @GET("/user")
    fun getAuthenticatedUser(): UserNetworkEntity

    @Headers(USER_HEADER)
    @GET("/users/{username}")
    fun getUser(@Path("username") username:String): UserNetworkEntity

    @Headers(USER_HEADER)
    @GET("/user/followers")
    fun getAutheticatedUserFollowers():List<UserNetworkEntity>
}