package com.emilio.popularmovie.ui.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.emilio.popularmovie.R
import com.emilio.popularmovie.common.OnSelectItem
import com.emilio.popularmovie.common.TypeClick
import com.emilio.popularmovie.databinding.FragmentPopularMovieBinding
import com.emilio.popularmovie.domain.movieList.ViewModelFactory
import com.emilio.popularmovie.domain.movieList.MovieListViewModel
import com.emilio.popularmovie.domain.movieList.ViewModelType
import com.emilio.popularmovie.model.FavMovie
import com.emilio.popularmovie.model.Movie
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.service.MovieServiceProvider
import com.google.android.material.appbar.MaterialToolbar


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
        viewModel.getMovies()

        setEventListener()
        setObservers()

    }

    private fun setEventListener() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                text?.let {
                    viewModel.searchMovie(it)
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

        binding.previousPage.setOnClickListener {
            viewModel.previousPage()
        }

        binding.nextPage.setOnClickListener {
            viewModel.nextPage()
        }
    }

    private fun setObservers() {
        viewModel.listMovies.observe(this) { listMovies ->
            setUpAdapter(listMovies)
        }
    }

    private fun setUpAdapter(listMovies: MutableList<Movie>) {

        val movieAdapter: MovieAdapter = MovieAdapter(listMovies)
        movieAdapter.setOnSelectListener(object : OnSelectItem {

            override fun onSelect(view: View, pos: Int, typeClick: TypeClick) {
                viewModel.itemSelected(pos, typeClick)
            }

        })


        binding.moviesContainer.apply {
            layoutManager = GridLayoutManager(activity, 3)
            adapter = movieAdapter
        }
    }

}