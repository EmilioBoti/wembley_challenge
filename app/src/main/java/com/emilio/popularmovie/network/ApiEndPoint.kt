package com.emilio.popularmovie.network

import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.AuthEndPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface ApiEndPoint: AuthEndPoint {

    @GET("3/movie/popular")
    fun retrieveMovies(@QueryMap params: HashMap<String, String>): Call<Page>

    @GET("3/search/movie")
    fun searchMovies(@QueryMap params: HashMap<String, String>): Call<Page>

}