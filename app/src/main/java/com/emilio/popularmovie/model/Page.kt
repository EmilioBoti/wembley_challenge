package com.emilio.popularmovie.model

data class Page(
    val page: Int,
    val results: MutableList<Movie>,
    val total_pages: Int,
    val total_results: Int
)
