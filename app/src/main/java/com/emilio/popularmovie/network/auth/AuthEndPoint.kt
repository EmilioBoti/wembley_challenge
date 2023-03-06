package com.emilio.popularmovie.network.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface AuthEndPoint {

    @GET("3/authentication/token/new")
    suspend fun requestTokenService(@QueryMap params: HashMap<String, String>): RequestToken

    @GET("3/account")
    suspend fun requestAccountService(@QueryMap params: HashMap<String, String>): Account

    @Headers("Content-type: application/json")
    @POST("3/authentication/token/validate_with_login")
    suspend fun authWithLoginService(@Body body: User, @QueryMap params: HashMap<String, String>): RequestToken

    @Headers("Content-type: application/json")
    @POST("3/authentication/session/new")
    suspend fun createSessionService(@Body body: Token, @QueryMap params: HashMap<String, String>): SessionStatus


}