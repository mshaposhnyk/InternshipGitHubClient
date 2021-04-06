package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.UserRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.User
import com.example.core.domain.Result

class GetUser(
    private val userRepository: UserRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(): Result<User> {
        return try {
            Result.Success(userRepository.getUser())
        } catch (ex:Throwable){
            Result.Error(errorHandler.getError(ex))
        }
    }
}