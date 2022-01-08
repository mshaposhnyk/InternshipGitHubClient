package com.example.core.data

import com.example.core.domain.ErrorEntity
import com.example.core.domain.ErrorHandler
import java.io.IOException

class NetworkErrorHandler : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable){
            is IOException -> ErrorEntity.ServiceUnavailable
            else -> ErrorEntity.Unknown
        }
    }
}