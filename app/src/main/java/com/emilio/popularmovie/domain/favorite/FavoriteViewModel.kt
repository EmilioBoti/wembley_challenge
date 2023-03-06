package com.emilio.popularmovie.domain.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilio.popularmovie.domain.movieList.ISession
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.UserSession
import com.emilio.popularmovie.service.IRepository

class FavoriteViewModel(private val repository: IRepository): ViewModel(), ISession {
    private var session: MutableMap<String, *>? = null
    private var page: Page? = null
    private var _favoriteMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    var favoriteMovies: LiveData<MutableList<Movie>> = _favoriteMovies

    override fun setUpSession(context: Context?) {
        session = UserSession.getSession(context)
    }

    suspend fun getFavoriteMovies() {
        session?.let {
            val params = UserSession.getApiKeyParam().apply {
                put("session_id", it[UserSession.SESSION_ID] as String)
            }
            page = session?.let {
                repository.retrieveFavoriteMovie(it[UserSession.ACCOUNT_ID] as Int, params)
            }

            page?.let {
                updateList(it.results)
            }

        }
    }

    private fun updateList(list: MutableList<Movie>) {
        _favoriteMovies.postValue(list)
    }

}