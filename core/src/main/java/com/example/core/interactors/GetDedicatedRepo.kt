package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User

class GetDedicatedRepo(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(user: User, nameRepo: String): Result<Repo> {
        return try {
            Result.Success(repoRepository.getDedicatedRepo(user, nameRepo))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError(ex))
        }
    }
}