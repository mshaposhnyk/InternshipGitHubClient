package com.example.core.interactors

import com.example.core.data.IssueRepository
import com.example.core.domain.Repo

class GetRepoIssues(private val issueRepository: IssueRepository) {
    operator fun invoke(repo: Repo) = issueRepository.getRepoIssues(repo)
}