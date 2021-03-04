package com.example.internshipgithubclient.login.network

import com.example.internshipgithubclient.login.network.user.UserApiService

class NetworkUtils {
    companion object{
        private lateinit var userService: UserApiService

        fun getUserApiInstance():UserApiService{
            if(!this::userService.isInitialized){
                userService = NetworkClient.retrofit.create(UserApiService::class.java)
            }
            return userService
        }
    }
}