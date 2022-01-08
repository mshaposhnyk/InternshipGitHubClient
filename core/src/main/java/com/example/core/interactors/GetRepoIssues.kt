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
    operator fun invoke(repo: Repo): Result<Single<List<Issue>>> {
        return try {
            Result.Success(issueRepository.getRepoIssues(repo))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError((ex)))
        }
    }
}