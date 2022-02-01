package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.*
import io.reactivex.Single

class GetAllUserRepos(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    operator fun invoke(user: User): Single<Result<List<Repo>>> {
        return repoRepository.getAllUserRepos(user)
            .onErrorReturn {
                Result.Error(errorHandler.getError(it))
            }
    }
}