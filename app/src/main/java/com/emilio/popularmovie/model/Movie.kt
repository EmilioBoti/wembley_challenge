package com.emilio.popularmovie.model

data class Movie(
    val id: Int,
    val original_title: String,
    val title: String,
    val overview: String,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float,
    val vote_count: Int
)
