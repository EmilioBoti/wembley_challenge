package com.emilio.popularmovie.network.auth

data class User(
    val username: String,
    val password: String,
    val request_token: String
)
