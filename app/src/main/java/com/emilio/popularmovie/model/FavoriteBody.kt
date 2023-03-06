package com.emilio.popularmovie.model

data class FavoriteBody(
    val media_type: String,
    val media_id: Int,
    val favorite: Boolean
)
