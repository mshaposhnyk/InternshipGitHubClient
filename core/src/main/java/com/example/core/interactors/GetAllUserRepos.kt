package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.*
import kotlinx.coroutines.flow.Flow

class GetAllUserRepos(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(user: User): Result<Flow<Repo>> {
        return try {
            Result.Success(repoRepository.getAllUserRepos(user))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError(ex))
        }
    }
}