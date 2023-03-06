package com.emilio.popularmovie.service

import com.emilio.popularmovie.model.FavoriteBody
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.Account

interface IRepository: IAuth {
    suspend fun retrieveMovies(params: HashMap<String, String>): Page?
    suspend fun searchMovies(params: HashMap<String, String>): Page?
    suspend fun retrieveFavoriteMovie(accountId: Int, params: HashMap<String, String>): Page?
    suspend fun markFavoriteService(accountId: Int, body: FavoriteBody, params: HashMap<String, String>): Status?
    suspend fun accountService(params: HashMap<String, String>): Account?
}