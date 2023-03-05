package com.emilio.popularmovie.service

import com.emilio.popularmovie.model.Page

interface IRepository: IAuth {
    suspend fun retrieveMovies(params: HashMap<String, String>): Page?
    suspend fun searchMovies(params: HashMap<String, String>): Page?
}