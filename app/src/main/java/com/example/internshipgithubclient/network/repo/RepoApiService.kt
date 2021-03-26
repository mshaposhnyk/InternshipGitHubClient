package com.example.internshipgithubclient.network.repo

import com.example.internshipgithubclient.network.pull.PullNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApiService {
    //Get repos pulls
    @GET("/repos/{owner}/{repo}/pulls")
    fun getPullsForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all"
    ): Single<List<PullNetworkEntity>>

    //Get issues for user repo
    @GET("/repos/{owner}/{repo}/issues")
    fun getIssuesForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all"
    ): Single<List<IssueNetworkEntity>>

    //Get people watching repository
    @GET("/repos/{owner}/{repo}/subscribers")
    fun getWatchersForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<List<UserNetworkEntity>>

    //Get a user repository
    @GET("/repos/{owner}/{repo}")
    fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<RepoNetworkEntity>

    //Delete a user repository
    @DELETE("/repos/{owner}/{repo}")
    fun deleteRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Void>

    //Get list of public repositories
    @GET("/repositories")
    fun getPublicRepos(): Single<List<RepoNetworkEntity>>

    //Get user repositories
    @GET("/users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Single<List<RepoNetworkEntity>>
}