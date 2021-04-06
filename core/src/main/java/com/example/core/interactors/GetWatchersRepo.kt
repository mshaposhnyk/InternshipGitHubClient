package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import kotlinx.coroutines.flow.Flow

class GetWatchersRepo(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(repo: Repo): Result<Flow<User>> {
        return try {
            Result.Success(repoRepository.getWatchersRepo(repo))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError(ex))
        }
    }
}