package com.example.core.interactors

import com.example.core.data.PullRepository
import com.example.core.domain.Repo

class GetRepoPulls(private val pullRepository: PullRepository) {
    suspend operator fun invoke(repo: Repo)=pullRepository.getRepoPulls(repo)
}