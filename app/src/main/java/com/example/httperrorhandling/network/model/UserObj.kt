package com.example.httperrorhandling.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserObj(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String
)