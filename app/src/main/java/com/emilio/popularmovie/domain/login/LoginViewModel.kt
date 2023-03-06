package com.emilio.popularmovie.domain.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilio.popularmovie.domain.movieList.ISession
import com.emilio.popularmovie.network.auth.*
import com.emilio.popularmovie.service.IRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: IRepository): ViewModel(), AuthUseCase, ISession {
    var login: MutableLiveData<Boolean> = MutableLiveData()
    private var session: MutableMap<String, *>? = null


    override fun requestToken(context: Context?, username: String, password: String) {

        viewModelScope.launch {
            val token: RequestToken? = async {
                repository.createToken(getApiKeyParam())
            }.await()

            val req: RequestToken? = async {
                token?.request_token?.let {
                    validateTokenWithLogin(User(username, password, it))
                }
            }.await()

            req?.request_token?.let {
                val session = async {
                    token?.request_token?.let {
                        createSession(Token(it), getApiKeyParam())
                    }
                }.await()

                session?.let {
                    val params = getApiKeyParam().apply {
                        put("session_id", it.session_id)
                    }
                    val account = getAccount(params)
                    account?.id?.let { accountId ->
                        UserSession.saveSession(context, it.session_id, accountId, username)
                        login.postValue(it.success)
                    }
                }
            }
        }

    }

    suspend fun getAccount(params: HashMap<String, String>): Account? {
        return repository.accountService(params)
    }

    override suspend fun validateTokenWithLogin(user: User?): RequestToken? {
        return user?.let {
            repository.authWithLogin(user, getApiKeyParam())
        }
    }

    override suspend fun createSession(token: Token, params: HashMap<String, String>): SessionStatus? {
        return repository.createSessionService(token, params)
    }

    private fun getApiKeyParam(): HashMap<String, String> = HashMap<String, String>().apply {
        put("api_key", "f4d9eef562ddac8945db4d955d7a86c5")
    }

    override fun setUpSession(context: Context?) {
        session = UserSession.getSession(context)

        if (isLogin()) {
            login.postValue(true)
        }
    }

    private fun isLogin(): Boolean {
        return session?.containsKey(UserSession.ACCOUNT_ID) == true
                && session?.containsKey(UserSession.SESSION_ID) == true
    }

}