package com.example.httperrorhandling.network.di

import android.util.Log
import com.example.httperrorhandling.network.adapter.ErrorHandlingCallAdapterFactory
import com.example.httperrorhandling.network.api.TestingErrorApi
import com.example.httperrorhandling.network.errors.ErrorHandler
import com.example.httperrorhandling.network.errors.StandardErrorHandler
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideConverter(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @[Provides Singleton]
    fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }

    /**
     * Factory for [calls][Call], which can be used to send HTTP requests and read their responses.
     */
    @[Provides Singleton]
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder().apply {
                    // headers
                    addHeader("accept", "application/json")
                }
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Log.d("OkHttp", message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    /**
     * Retrofit adapts a Java interface to HTTP calls by using annotations on the declared methods to define how requests are made.
     */
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory,
        callFactory: ErrorHandlingCallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://httperror.free.beeceptor.com")
            .addConverterFactory(converter)
            .addCallAdapterFactory(callFactory)
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): TestingErrorApi {
        return retrofit.create()
    }

    @Provides
    fun provideErrorHandler(standardErrorHandler: StandardErrorHandler): ErrorHandler {
        return standardErrorHandler
    }
}
