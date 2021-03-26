package com.example.core.interactors

import com.example.core.data.RepoRepository
import com.example.core.domain.User

class GetAllUserRepos(private val repoRepository: RepoRepository) {
    operator fun invoke(user: User) = repoRepository.getAllUserRepos(user)
}