package com.emilio.popularmovie.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.emilio.popularmovie.databinding.ActivityLoginBinding
import com.emilio.popularmovie.domain.login.LoginViewModel
import com.emilio.popularmovie.domain.movieList.ViewModelFactory
import com.emilio.popularmovie.domain.movieList.ViewModelType
import com.emilio.popularmovie.network.RetrofitBuilder
import com.emilio.popularmovie.service.MovieServiceProvider
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        viewModelFactory = ViewModelFactory(MovieServiceProvider(RetrofitBuilder.getInstance()), ViewModelType.LOGIN)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        loginViewModel.setUpSession(this)

        setUpEventListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        loginViewModel.login.observe(this) {
            navigateTo()
        }
    }

    private fun setUpEventListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            lifecycleScope.launch {
                loginViewModel.requestToken(this@LoginActivity, username, password)
            }
        }

    }

    private fun navigateTo() {
        Intent(this, HomeActivity::class.java).apply {
            startActivity(this)
        }
        this.finish()
    }
}