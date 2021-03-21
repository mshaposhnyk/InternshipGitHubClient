package com.example.core.interactors

import com.example.core.data.UserRepository

class GetUser(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUser()
}