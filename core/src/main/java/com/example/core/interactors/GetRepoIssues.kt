package com.example.core.interactors

import com.example.core.data.IssueRepository
import com.example.core.data.NetworkErrorHandler
import com.example.core.domain.ErrorHandler
import com.example.core.domain.Issue
import com.example.core.domain.Repo
import kotlinx.coroutines.flow.Flow
import com.example.core.domain.Result

class GetRepoIssues(
    private val issueRepository: IssueRepository,
    private val errorHandler: ErrorHandler = NetworkErrorHandler()
) {
    suspend operator fun invoke(repo: Repo): Result<Flow<Issue>> {
        return try {
            Result.Success(issueRepository.getRepoIssues(repo))
        } catch (ex: Throwable) {
            Result.Error(errorHandler.getError((ex)))
        }
    }
}