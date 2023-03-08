package com.emilio.popularmovie.domain.movieList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.model.FavMovie
import com.emilio.popularmovie.model.FavoriteBody
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.model.Page
import com.emilio.popularmovie.network.auth.UserSession
import com.emilio.popularmovie.service.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: IRepository): ViewModel(), IMovie, ISession {
    private var _listMovies: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    var listMovies: LiveData<MutableList<Movie>> = _listMovies
    private var currentPage: Page? = null
    private var session: MutableMap<String, *>? = null


    override fun getMovies(page: Int) {
        val params = UserSession.getApiKeyParam().apply {
            put("page", page.toString())
        }

        viewModelScope.launch {
            val res = repository.retrieveMovies(params)
            res?.let {
                currentPage = it
                updateList(it.results)
            }
        }

    }

    override fun getMoviesDetail(movie: Movie?) {

    }

    override fun addToFavorite(pos: Int, typeClick: TypeClick) {
        val movie = listMovies.value?.get(pos)
        when(typeClick) {
            TypeClick.NONE -> { getMoviesDetail(movie) }
            TypeClick.FAVORITE -> { markFavorite(movie, true) }
            TypeClick.NOT_FAVORITE -> { markFavorite(movie, false)}
        }
    }

    private fun markFavorite(movie: Movie?, isFavorite: Boolean) {
        session?.let {
            viewModelScope.launch {
                val params = UserSession.getApiKeyParam().apply {
                    put("session_id", it[UserSession.SESSION_ID] as String)
                }
                movie?.id?.let { movieId ->
                    addAndRemoveToFavorite(it[UserSession.ACCOUNT_ID] as Int, FavoriteBody("movie", movieId, isFavorite), params)
                }
            }
        }
    }
    private suspend fun addAndRemoveToFavorite(accountId: Int, favBody: FavoriteBody, params: HashMap<String, String>) {
        repository.markFavoriteService(accountId, favBody, params)
    }

    override fun searchMovie(name: String) {
        val params = UserSession.getApiKeyParam().apply {
            put("query", name)
        }

        viewModelScope.launch {
            val res = repository.searchMovies(params)
            res?.let {
                updateList(it.results)
            }
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

    fun previousPage() {
        currentPage?.let {
            val p = it.page-1
            changePage(p)
        }
    }

    fun nextPage() {
        currentPage?.let {
            val p = it.page+1
            changePage(p)
        }
    }

    private fun changePage(page: Int) {
        if (page in  0..10000) {
            viewModelScope.launch {
                getMovies(page)
            }
        }
    }

    fun itemSelected(pos: Int, typeClick: TypeClick) {
        addToFavorite(pos, typeClick)
    }

}