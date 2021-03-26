package com.example.core.interactors

import com.example.core.data.RepoRepository
import com.example.core.domain.Repo

class GetWatchersRepo(private val repoRepository: RepoRepository) {
    operator fun invoke(repo: Repo) = repoRepository.getWatchersRepo(repo)
}