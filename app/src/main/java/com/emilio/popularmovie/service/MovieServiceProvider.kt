package com.emilio.popularmovie.service

import android.util.Log
import com.emilio.popularmovie.model.FavoriteBody
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.ApiEndPoint
import com.emilio.popularmovie.network.auth.Account
import com.emilio.popularmovie.network.auth.RequestToken
import com.emilio.popularmovie.network.auth.Token
import com.emilio.popularmovie.network.auth.User
import com.emilio.popularmovie.network.auth.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MovieServiceProvider(private val retrofit: Retrofit): IRepository {
    private val TAG = "ERROr"

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
                Log.e(TAG, e.message?: "network Error")
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
                Log.e(TAG, e.message?: "network Error")
                null
            }
        }
    }

    override suspend fun retrieveFavoriteMovie(accountId: Int, params: HashMap<String, String>): Page? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).retrieveFavoriteMovies(accountId, params).execute()
                if (res.isSuccessful) {
                    res.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message?: "network Error")
                null
            }
        }
    }

    override suspend fun markFavoriteService(accountId: Int, body: FavoriteBody, params: HashMap<String, String>): Status? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).markAsFavorite(accountId, body, params).execute()
                res.body()
            }catch (e: Exception) {
                Log.e(TAG, e.cause?.message?: "network Error")
                null
            }
        }
    }

    override suspend fun accountService(params: HashMap<String, String>): Account? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).requestAccountService(params)
                res
            }catch (e: Exception) {
                Log.e(TAG, e.cause?.message?: "network Error")
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
                Log.e(TAG, e.cause?.message?: "network Error")
                null
            }
        }
    }

    override suspend fun createSessionService(body: Token, params: HashMap<String, String>): SessionStatus? {
        return withContext(Dispatchers.IO) {
            try {
                val res = retrofit.create(ApiEndPoint::class.java).createSessionService(body, params)
                if (res.success) {
                    res
                } else {
                    null
                }
            }catch (e: Exception) {
                Log.e(TAG, e.cause?.message?: "network Error")
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
                Log.e(TAG, e.cause?.message?: "network Error")
                null
            }
        }
    }

}