package com.emilio.popularmovie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.emilio.popularmovie.R
import com.emilio.popularmovie.databinding.ActivityHomeBinding
import com.emilio.popularmovie.domain.HomeViewModel
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.service.MovieServiceProvider
import com.emilio.popularmovie.ui.favorite.FavoriteMovieFragment
import com.emilio.popularmovie.ui.movie.MovieListFragment
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        navigateTo(MovieListFragment())
        eventListener()

    }

    private fun eventListener() {

        val repository = MovieServiceProvider(RetrofitBuilder.getInstance())
        homeViewModel.setUpRepository(repository)

        binding.toolbar.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            homeViewModel.logout()
            true
        }

        binding.tabsContainer.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        navigateTo(MovieListFragment())
                    }
                    1 -> {
                        navigateTo(FavoriteMovieFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        homeViewModel.isLogout.observe(this) {
            if (it) this.finish()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}