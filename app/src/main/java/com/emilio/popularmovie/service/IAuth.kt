package com.emilio.popularmovie.service

import com.emilio.popularmovie.network.auth.*

interface IAuth {
    suspend fun createToken(params: HashMap<String, String>): RequestToken?
    suspend fun createSessionService(body: Token, params: HashMap<String, String>): SessionStatus?
    suspend fun authWithLogin(body: User, params: HashMap<String, String>): RequestToken?
    suspend fun logoutSession(body: SessionLogout, params: HashMap<String, String>): Any?

}
