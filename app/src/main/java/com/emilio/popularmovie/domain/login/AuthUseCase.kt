package com.emilio.popularmovie.domain.login


import android.content.Context
import com.emilio.popularmovie.network.auth.RequestToken
import com.emilio.popularmovie.network.auth.Token
import com.emilio.popularmovie.network.auth.User

interface AuthUseCase {
    fun requestToken(username: String, password: String)
    suspend fun validateTokenWithLogin(user: User?) : RequestToken?
    suspend fun createSession(token: Token, params: HashMap<String, String>)
}