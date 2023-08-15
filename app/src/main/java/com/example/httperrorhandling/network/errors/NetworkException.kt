package com.example.httperrorhandling.network.errors

class NetworkException(override val message: String?, override val cause: Throwable?) : Exception()