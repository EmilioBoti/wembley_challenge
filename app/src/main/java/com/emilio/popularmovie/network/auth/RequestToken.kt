package com.emilio.popularmovie.network.auth

data class RequestToken(
    val success: Boolean,
    val expires_at: String,
    val request_token: String
)