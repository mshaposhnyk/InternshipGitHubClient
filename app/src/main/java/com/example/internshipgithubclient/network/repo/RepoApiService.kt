package com.example.internshipgithubclient.network.repo

import com.example.internshipgithubclient.network.pull.PullNetworkEntity
import com.example.internshipgithubclient.network.user.UserNetworkEntity
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApiService {
    //Get repos pulls
    @GET("/repos/{owner}/{repo}/pulls")
    suspend fun getPullsForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all"
    ): List<PullNetworkEntity>

    //Get issues for user repo
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssuesForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String = "all"
    ): List<IssueNetworkEntity>

    //Get people watching repository
    @GET("/repos/{owner}/{repo}/subscribers")
    suspend fun getWatchersForRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<UserNetworkEntity>

    //Get a user repository
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepoNetworkEntity

    //Delete a user repository
    @DELETE("/repos/{owner}/{repo}")
    fun deleteRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    )

    //Get list of public repositories
    @GET("/repositories")
    fun getPublicRepos():List<RepoNetworkEntity>

    //Get user repositories
    @GET("/users/{username}/repos")
    suspend fun getUserRepos(@Path("username") username: String): List<RepoNetworkEntity>
}