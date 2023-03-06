package com.emilio.popularmovie.network.auth

data class SessionStatus(
    val session_id: String,
    val success: Boolean,
    var account_id: Int
)
