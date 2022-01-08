package com.example.core.data

import com.example.core.domain.ErrorEntity
import org.junit.Assert
import org.junit.Test
import java.io.IOException
import java.lang.IllegalStateException

class NetworkErrorHandlerTest {

    @Test
    fun `check error entity`() {
        val networkErrorHandler = NetworkErrorHandler()
        val ioException = IOException()
        val illegalStateException = IllegalStateException()

        Assert.assertEquals(networkErrorHandler.getError(ioException), ErrorEntity.ServiceUnavailable)
        Assert.assertEquals(networkErrorHandler.getError(illegalStateException), ErrorEntity.Unknown)
    }
}