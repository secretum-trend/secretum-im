package com.messaging.scrtm.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class SessionPref @Inject constructor(context : Context)  {
    companion object {
        const val PREF_ADDRESS = "pref_address"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName + "_session", Context.MODE_PRIVATE)

    var address: String
        get() = sharedPreferences.getString(PREF_ADDRESS, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_ADDRESS, value).apply()

}