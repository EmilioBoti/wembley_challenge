package com.emilio.popularmovie.domain.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilio.popularmovie.domain.movieList.ISession
import com.emilio.popularmovie.network.auth.*
import com.emilio.popularmovie.service.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: IRepository): ViewModel(), AuthUseCase, ISession {
    var login: MutableLiveData<LoginValidation> = MutableLiveData()
    private var session: MutableMap<String, *>? = null


    override fun requestToken(context: Context?, username: String, password: String) {
        val params = getApiKeyParam()

        viewModelScope.launch {

            val token: RequestToken? = getRequestedToken()

            val validToken: RequestToken? = token?.request_token?.let {
                validateToken(User(username, password, it))
            }

            val sessionStatus = validToken?.let {
                getSession(it.request_token)
            }

            sessionStatus?.let { sessionStatus ->
                params["session_id"] = sessionStatus.session_id

                val account = getAccount(params)
                session?.let {
                    account?.id?.let { accountId ->
                        UserSession.saveSession(context, sessionStatus.session_id, accountId, username)
                        login.postValue(LoginValidation.SUCCESS)
                    }
                }

            } ?: login.postValue(LoginValidation.FAIL)

        }

    }

    private suspend fun getSession(requestToken: String): SessionStatus? {
        return withContext(Dispatchers.Default) {
            createSession(Token(requestToken), getApiKeyParam())
        }
    }
    private suspend fun getRequestedToken() : RequestToken? {
        val token: RequestToken? = withContext(Dispatchers.Default) {
            repository.createToken(getApiKeyParam())
        }
        return token
    }

    suspend fun getAccount(params: HashMap<String, String>): Account? {
        return repository.accountService(params)
    }
    private suspend fun validateToken(user: User): RequestToken? {
        return withContext(Dispatchers.Default) {
            validateTokenWithLogin(user)
        }
    }

    override suspend fun validateTokenWithLogin(user: User?): RequestToken? {
        return user?.let {
            repository.authWithLogin(user, getApiKeyParam())
        }
    }

    override suspend fun createSession(token: Token, params: HashMap<String, String>): SessionStatus? {
        return repository.createSessionService(token, params)
    }

    private fun getApiKeyParam(): HashMap<String, String> = UserSession.getApiKeyParam()

    override fun setUpSession(context: Context?) {
        session = UserSession.getSession(context)

        if (isLogin()) {
            login.postValue(LoginValidation.SUCCESS)
        }
    }

    private fun isLogin(): Boolean {
        return session?.containsKey(UserSession.ACCOUNT_ID) == true
                && session?.containsKey(UserSession.SESSION_ID) == true
    }

}