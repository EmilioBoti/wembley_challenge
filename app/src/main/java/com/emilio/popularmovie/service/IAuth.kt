package com.emilio.popularmovie.service

import com.emilio.popularmovie.network.auth.RequestToken
import com.emilio.popularmovie.network.auth.Session
import com.emilio.popularmovie.network.auth.Token
import com.emilio.popularmovie.network.auth.User

interface IAuth {
    suspend fun createToken(params: HashMap<String, String>): RequestToken?
    suspend fun createSessionService(body: Token, params: HashMap<String, String>): Session?
    suspend fun authWithLogin(body: User, params: HashMap<String, String>): RequestToken?
}
