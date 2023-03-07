package com.emilio.popularmovie.network.auth

data class SessionLogout(
    val session_id: String,
    val success: Boolean
)
