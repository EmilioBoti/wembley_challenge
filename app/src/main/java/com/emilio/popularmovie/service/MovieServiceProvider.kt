package com.emilio.popularmovie.service

import android.util.Log
import com.emilio.popularmovie.domain.Const
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.ApiEndPoint
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.network.auth.RequestToken
import com.emilio.popularmovie.network.auth.Session
import com.emilio.popularmovie.network.auth.Token
import com.emilio.popularmovie.network.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieServiceProvider(private val retrofit: Retrofit): IRepository {

    override suspend fun retrieveMovies(params: HashMap<String, String>): Page? {

        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).retrieveMovies(params).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message?: "network Error")
                null
            }
        }
    }

    override suspend fun searchMovies(params: HashMap<String, String>): Page? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).searchMovies(params).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message?: "network Error")
                null
            }
        }
    }

    override suspend fun createToken(params: HashMap<String, String>): RequestToken? {

        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).requestTokenService(params)
                if (res.success) {
                    res
                } else {
                    null
                }
            }catch (e: Exception) {
                Log.e("ERROR-AUTH", e.cause?.message?: "network Error")
                null
            }
        }
    }

    override suspend fun createSessionService(body: Token, params: HashMap<String, String>): Session? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).createSessionService(body, params)
                if (res.success) {
                    res
                } else {
                    null
                }
            }catch (e: Exception) {
                Log.e("ERROR-AUTH", e.cause?.message?: "network Error")
                null
            }
        }
    }

    override suspend fun authWithLogin(body: User, params: HashMap<String, String>): RequestToken? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).authWithLoginService(body, params)
                if (res.success) {
                    res
                } else {
                    null
                }
            }catch (e: Exception) {
                throw e
                Log.e("ERROR-AUTH", e.cause?.message?: "network Error")
                null
            }
        }
    }

}