package com.emilio.popularmovie.domain.movieList

interface IMovie {
    suspend fun getMovies(page: Int = 1)
    suspend fun getMoviesDetail(pos: Int)
    suspend fun addToFavorite(pos: Int)
    suspend fun searchMovie(name: String)
}