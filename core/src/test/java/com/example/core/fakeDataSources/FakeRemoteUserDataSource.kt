package com.example.core.fakeDataSources

import com.example.core.createTestUser
import com.example.core.data.RemoteUserDataSource
import io.reactivex.Single

class FakeRemoteUserDataSource : RemoteUserDataSource {
    override fun get() = Single.just(createTestUser(1))
}