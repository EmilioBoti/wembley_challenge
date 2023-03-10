package com.emilio.popularmovie.network.auth

import android.content.Context
import com.emilio.popularmovie.BuildConfig
import com.emilio.popularmovie.R

object UserSession {
    private const val API_KEY = BuildConfig.SECRET_APIKEY
    const val SESSION_ID = "session_id"
    const val ACCOUNT_ID = "account_id"
    const val USERNAME = "username"

    fun saveSession(context: Context?, sessionId: String, accountId: Int, username: String) {
        context?.getSharedPreferences(context.getString(R.string.user_session), Context.MODE_PRIVATE)?.edit()
        ?.putString(USERNAME, username)
        ?.putString(SESSION_ID, sessionId)
        ?.putInt(ACCOUNT_ID, accountId)
        ?.apply()
    }

    fun logoutSession(context: Context?) {
        context?.getSharedPreferences(context.getString(R.string.user_session), Context.MODE_PRIVATE)?.edit()
            ?.clear()
            ?.apply()
    }
    
    fun getSession(context: Context?): MutableMap<String, *>? {
        return context?.getSharedPreferences(context.getString(R.string.user_session), Context.MODE_PRIVATE)?.all
    }

    fun getApiKeyParam(): HashMap<String, String> = HashMap<String, String>().apply {
        put("api_key", API_KEY)
    }

}