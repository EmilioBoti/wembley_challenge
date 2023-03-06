package com.emilio.popularmovie.network

import com.emilio.popularmovie.model.FavoriteBody
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.AuthEndPoint
import com.emilio.popularmovie.service.Status
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body


interface ApiEndPoint: AuthEndPoint {

    @GET("3/movie/popular")
    fun retrieveMovies(@QueryMap params: HashMap<String, String>): Call<Page>

    @GET("3/search/movie")
    fun searchMovies(@QueryMap params: HashMap<String, String>): Call<Page>

    @GET("3/account/{account_id}/favorite/movies")
    fun retrieveFavoriteMovies(@Path("account_id") accountId: Int, @QueryMap params: HashMap<String, String>): Call<Page>

    @Headers("Content-type: application/json;charset=utf-8")
    @POST("3/account/{account_id}/favorite")
    fun markAsFavorite(@Path("account_id") accountId: Int, @Body body: FavoriteBody, @QueryMap params: HashMap<String, String>): Call<Status>

}