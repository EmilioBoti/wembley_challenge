package com.emilio.popularmovie.ui.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.emilio.popularmovie.R
import com.emilio.popularmovie.common.OnSelectItem
import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.databinding.FragmentPopularMovieBinding
import com.emilio.popularmovie.domain.movieList.ViewModelFactory
import com.emilio.popularmovie.domain.movieList.MovieListViewModel
import com.emilio.popularmovie.domain.movieList.ViewModelType
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.service.MovieServiceProvider
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch


class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentPopularMovieBinding
    private lateinit var viewModel: MovieListViewModel
    private var toolbar: MaterialToolbar? = null
    private lateinit var searchBar: SearchView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPopularMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()


        val homeViewModelFactory = ViewModelFactory(MovieServiceProvider(RetrofitBuilder.getInstance()), ViewModelType.MOVIE_LIST)
        viewModel = ViewModelProvider(this, homeViewModelFactory)[MovieListViewModel::class.java]

        toolbar = activity?.findViewById(R.id.toolbar)
        searchBar = toolbar?.menu?.findItem(R.id.searcher)?.actionView as SearchView

        viewModel.setUpSession(activity)

        lifecycleScope.launchWhenStarted {
            viewModel.getMovies()
        }

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                text?.let {
                    lifecycleScope.launch {
                        viewModel.searchMovie(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if(text?.isEmpty() == true) {
                    viewModel.resetSearch()
                }
                return true
            }

        })

        setObservers()

    }

    private fun setObservers() {
        viewModel.listMovies.observe(this) { listMovies ->
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

                        activity?.applicationContext?.let { context ->
                            v.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_liked))
                            lifecycleScope.launch {
                                viewModel.addToFavorite(pos)
                            }
                        }
                    }
                    TypeClick.NONE -> {

                    }
                    null -> {
                        lifecycleScope.launch {
                            viewModel.getMoviesDetail(pos)
                        }
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