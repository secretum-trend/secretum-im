package com.messaging.scrtm.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class SessionPref @Inject constructor(context : Context)  {
    companion object {
        const val PREF_ADDRESS = "pref_address"
        const val PREF_AUTH_TOKEN = "pref_auth_token"
        const val PREF_ACCESS_TOKEN = "pref_accessToken"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName + "_session", Context.MODE_PRIVATE)

    var address: String
        get() = sharedPreferences.getString(PREF_ADDRESS, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_ADDRESS, value).apply()

    var authToken : String
        get() = sharedPreferences.getString(PREF_AUTH_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_AUTH_TOKEN, value).apply()

    var accessToken : String
        get() = sharedPreferences.getString(PREF_ACCESS_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_ACCESS_TOKEN, value).apply()
}