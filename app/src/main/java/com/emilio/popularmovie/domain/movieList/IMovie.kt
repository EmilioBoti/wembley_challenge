package com.emilio.popularmovie.domain.movieList

import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.model.Movie

interface IMovie {
    fun getMovies(page: Int = 1)
    fun getMoviesDetail(movie: Movie?)
    fun addToFavorite(pos: Int, typeClick: TypeClick)
    fun searchMovie(name: String)
}