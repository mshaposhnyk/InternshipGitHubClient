package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.PullRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Pull
import com.example.core.domain.Repo
import com.example.core.domain.Result
import kotlinx.coroutines.flow.Flow

class GetRepoPulls(
    private val pullRepository: PullRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(repo: Repo): Result<Flow<Pull>> {
        return try {
            Result.Success(pullRepository.getRepoPulls(repo))
        } catch (ex: Throwable){
            Result.Error(errorHandler.getError((ex)))
        }
    }
}