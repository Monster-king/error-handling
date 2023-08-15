package com.example.httperrorhandling.network.errors

class UnknownException(override val message: String?, override val cause: Throwable?) :
    Exception()