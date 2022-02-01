package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.UserRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.User
import com.example.core.domain.Result
import io.reactivex.Single

class GetUser(
    private val userRepository: UserRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    operator fun invoke(): Single<Result<User>> {
        return userRepository.getUser()
            .onErrorReturn {
                Result.Error(errorHandler.getError(it))
            }
    }
}