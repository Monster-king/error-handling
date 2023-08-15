package com.example.httperrorhandling.network.api

import com.example.httperrorhandling.network.model.UserObj
import retrofit2.http.GET
import retrofit2.http.Path

interface TestingErrorApi {

    @GET("/user/{type}")
    suspend fun getUser(@Path("type") type: Int): UserObj
}