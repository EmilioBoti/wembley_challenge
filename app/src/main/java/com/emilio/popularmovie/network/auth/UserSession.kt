package com.emilio.popularmovie.network.auth

import android.content.Context
import com.emilio.popularmovie.R

object UserSession {
    private const val API_KEY = "f4d9eef562ddac8945db4d955d7a86c5"
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

    fun getSession(context: Context?): MutableMap<String, *>? {
        return context?.getSharedPreferences(context.getString(R.string.user_session), Context.MODE_PRIVATE)?.all
    }

    fun getApiKeyParam(): HashMap<String, String> = HashMap<String, String>().apply {
        put("api_key", API_KEY)
    }

}