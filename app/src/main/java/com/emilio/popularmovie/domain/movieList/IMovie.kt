package com.emilio.popularmovie.domain.movieList

interface IMovie {
    fun getMovies(page: Int = 1)
    fun getMoviesDetail(pos: Int)
    fun addToFavorite(pos: Int)
    fun searchMovie(name: String)
}