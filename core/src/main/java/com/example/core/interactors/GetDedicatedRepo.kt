package com.example.core.interactors

import com.example.core.data.RepoRepository
import com.example.core.domain.User

class GetDedicatedRepo(private val repoRepository: RepoRepository) {
    suspend operator fun invoke(user: User, nameRepo: String) =
        repoRepository.getDedicatedRepo(user, nameRepo)
}