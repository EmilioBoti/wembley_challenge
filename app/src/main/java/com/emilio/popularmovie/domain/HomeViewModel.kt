package com.emilio.popularmovie.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emilio.popularmovie.network.auth.SessionLogout
import com.emilio.popularmovie.network.auth.UserSession
import com.emilio.popularmovie.service.IRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {
    private var session: MutableMap<String, *>? = null
    var isLogout: MutableLiveData<Boolean> = MutableLiveData()
    private var repository: IRepository? = null

    init {
        session = UserSession.getSession(application)
    }


    fun logout() {
        val body: SessionLogout? = session?.let {
            SessionLogout(it[UserSession.SESSION_ID] as String, false)
        }
        val params = UserSession.getApiKeyParam()

        viewModelScope.launch {
            body?.let {
                repository?.logoutSession(body, params)
            }?.let {
                UserSession.logoutSession(application)
                isLogout.postValue(true)
            }

        }
    }

    fun setUpRepository(repository: IRepository?) {
        this.repository = repository
    }
}
