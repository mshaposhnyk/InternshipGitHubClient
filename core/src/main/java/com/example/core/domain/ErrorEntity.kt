package com.example.core.domain

sealed class ErrorEntity {
    object ServiceUnavailable : ErrorEntity()
    object Uknown : ErrorEntity()
}