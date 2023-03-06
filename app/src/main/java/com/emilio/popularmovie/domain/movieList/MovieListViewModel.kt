package com.emilio.popularmovie.domain.movieList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilio.popularmovie.model.FavoriteBody
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.SessionStatus
import com.emilio.popularmovie.network.auth.UserSession
import com.emilio.popularmovie.service.IRepository
import com.emilio.popularmovie.service.Status

class MovieListViewModel(private val repository: IRepository): ViewModel(), IMovie, ISession {
    private var _listMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    var listMovies: LiveData<MutableList<Movie>> = _listMovies
    private var currentPage: Page? = null
    private var session: MutableMap<String, *>? = null


    override suspend fun getMovies(page: Int) {
        val params = UserSession.getApiKeyParam().apply {
            put("page", page.toString())
        }

        val res = repository.retrieveMovies(params)
        res?.let {
            currentPage = it
            updateList(it.results)
        }

    }

    override suspend fun getMoviesDetail(pos: Int) {
        val movie = listMovies.value?.get(pos)
    }

    override suspend fun addToFavorite(pos: Int) {
        val movie = listMovies.value?.get(pos)

        session?.let {
            val params = UserSession.getApiKeyParam().apply {
                put("session_id", it[UserSession.SESSION_ID] as String)
            }
            val status: Status? = movie?.id?.let { movieId ->
                repository.markFavoriteService(it[UserSession.ACCOUNT_ID] as Int, FavoriteBody("movie", movieId, true), params)
            }
        }
    }

    override suspend fun searchMovie(name: String) {
        val params = UserSession.getApiKeyParam().apply {
            put("query", name)
        }

        val res = repository.searchMovies(params)
        res?.let {
            updateList(it.results)
        }
    }

    fun resetSearch() {
        currentPage?.results?.let { updateList(it) }
    }

    private fun updateList(results: MutableList<Movie>) {
        _listMovies.postValue(results)
    }

    override fun setUpSession(context: Context?) {
        session = UserSession.getSession(context)
    }


}