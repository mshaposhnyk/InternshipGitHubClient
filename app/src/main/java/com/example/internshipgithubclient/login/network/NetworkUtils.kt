package com.example.internshipgithubclient.login.network

import com.example.internshipgithubclient.login.network.user.UserServiceAPI

class NetworkUtils {
    companion object{
        private lateinit var userService: UserServiceAPI

        fun getUserApiInstance():UserServiceAPI{
            if(!this::userService.isInitialized){
                userService = NetworkClient.retrofit.create(UserServiceAPI::class.java)
            }
            return userService
        }
    }
}