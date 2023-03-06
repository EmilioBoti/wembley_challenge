package com.emilio.popularmovie.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.emilio.popularmovie.R
import com.emilio.popularmovie.common.OnSelectItem
import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.databinding.FragmentFavoriteMovieBinding
import com.emilio.popularmovie.domain.favorite.FavoriteViewModel
import com.emilio.popularmovie.domain.login.LoginViewModel
import com.emilio.popularmovie.domain.movieList.ViewModelFactory
import com.emilio.popularmovie.domain.movieList.ViewModelType
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.service.MovieServiceProvider
import com.emilio.popularmovie.ui.movie.MovieAdapter
import kotlinx.coroutines.launch


class FavoriteMovieFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteMovieBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        viewModelFactory = ViewModelFactory(MovieServiceProvider(RetrofitBuilder.getInstance()), ViewModelType.FAVORITE)
        favoriteViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]

        favoriteViewModel.setUpSession(activity)

        lifecycleScope.launchWhenStarted {
            favoriteViewModel.getFavoriteMovies()
        }

        setObservers()
    }

    private fun setObservers() {
        favoriteViewModel.favoriteMovies.observe(this) { listMovies ->
            setUpAdapter(listMovies)
        }
    }

    private fun setUpAdapter(listMovies: MutableList<Movie>) {

        val movieAdapter: MovieAdapter = MovieAdapter(listMovies)
        movieAdapter.setOnSelectListener(object : OnSelectItem {

            override fun onSelect(view: View, pos: Int, typeClick: TypeClick?) {
                when(typeClick) {
                    TypeClick.FAVORITE ->  {
                        val v = view as AppCompatImageView
                    }
                    TypeClick.NONE -> {

                    }
                    null -> {

                    }
                }
            }

        })


        binding.moviesContainer.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = movieAdapter
        }
    }
}