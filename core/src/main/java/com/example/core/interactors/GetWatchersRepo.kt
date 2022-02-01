package com.example.core.interactors

import com.example.core.data.NetworkErrorHandler
import com.example.core.data.RepoRepository
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Repo
import com.example.core.domain.Result
import com.example.core.domain.User
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class GetWatchersRepo(
    private val repoRepository: RepoRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    operator fun invoke(repo: Repo): Single<Result<List<User>>> {
        return repoRepository.getWatchersRepo(repo)
            .onErrorReturn {
                Result.Error(errorHandler.getError(it))
            }
    }
}