package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.*
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class GetAllUserRepos(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    operator fun invoke(user: User): Result<Single<List<Repo>>> {
        return try {
            Result.Success(repoRepository.getAllUserRepos(user))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError(ex))
        }
    }
}