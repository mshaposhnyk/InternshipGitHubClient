package com.example.core.domain

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}