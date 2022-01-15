package com.example.core.interactors

import com.example.core.data.IssueRepository
import com.example.core.data.NetworkErrorHandler
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import kotlinx.coroutines.flow.Flow
import com.example.core.domain.Result
import io.reactivex.Single

class GetRepoIssues(
    private val issueRepository: IssueRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    operator fun invoke(repo: Repo): Single<Result<List<Issue>>> {
        return issueRepository.getRepoIssues(repo)
            .onErrorReturn {
                Result.Error(errorHandler.getError(it))
            }
    }
}