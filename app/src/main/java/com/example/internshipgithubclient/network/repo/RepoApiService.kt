package com.example.internshipgithubclient.network.repo

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoApiService {
    //Get a user repository
    @GET("/repos/{owner}/{repo}")
    fun getRepo(@Path("owner") owner:String, @Path("repo") repo:String):RepoNetworkEntity

    //Delete a user repository
    @DELETE("/repos/{owner}/{repo}")
    fun deleteRepo(@Path("owner") owner:String, @Path("repo") repo:String): Call<Void>

    //Get list of public repositories
    @GET("/repositories")
    fun getPublicRepos():List<RepoNetworkEntity>

    //Get user repositories
    @GET("/users/{username}/repos")
    fun getUserRepos(@Path("username") username:String):List<RepoNetworkEntity>
}