package com.example.httperrorhandling.network.repository

import com.example.httperrorhandling.network.api.TestingErrorApi
import com.example.httperrorhandling.network.model.UserObj
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestingErrorRepository @Inject constructor(
    private val api: TestingErrorApi
) {
    private var counter = 0

    fun getUser(): Flow<UserObj> {
        return flow {
            counter++
            emit(api.getUser((counter % 5) + 1))
        }
    }
}