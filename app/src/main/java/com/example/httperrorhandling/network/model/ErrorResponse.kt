package com.example.httperrorhandling.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(
    @SerialName("code") val code: Int? = null,
    @SerialName("message") val message: String? = null,
)