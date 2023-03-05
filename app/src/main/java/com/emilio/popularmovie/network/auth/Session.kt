package com.emilio.popularmovie.network.auth

data class Session(
    val success: Boolean,
    val session_id: String
)
