package com.emilio.popularmovie.domain.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emilio.popularmovie.domain.favorite.FavoriteViewModel
import com.emilio.popularmovie.domain.login.LoginViewModel
import com.emilio.popularmovie.service.IRepository

class ViewModelFactory(private val repository: IRepository, private val type: ViewModelType): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(type) {
            ViewModelType.LOGIN -> {
                LoginViewModel(repository) as T
            }
            ViewModelType.MOVIE_LIST -> {
                MovieListViewModel(repository) as T
            }
            ViewModelType.FAVORITE -> FavoriteViewModel(repository) as T
        }

    }
}