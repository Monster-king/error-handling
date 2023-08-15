package com.example.httperrorhandling.network.errors

interface ErrorHandler {

    fun handleError(error: Throwable)
}