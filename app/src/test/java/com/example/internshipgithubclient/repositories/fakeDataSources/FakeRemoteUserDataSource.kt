package com.example.internshipgithubclient.repositories.fakeDataSources

import com.example.core.data.RemoteUserDataSource
import com.example.internshipgithubclient.createTestUser

class FakeRemoteUserDataSource : RemoteUserDataSource {
    override suspend fun get() = createTestUser(1)
}