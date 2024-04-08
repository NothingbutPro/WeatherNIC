package com.parag.indianicweather.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit


import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

/**
 * App session
 *
 * @property sharedPref
 * @constructor Create empty App session
 */
@Singleton
class AppSession @Inject constructor(private var sharedPref: SharedPreferences) {
    private var editor: SharedPreferences.Editor? = null

    init {
        try {
            editor = sharedPref.edit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun resetAppSession(){
        editor = sharedPref.edit()

        editor?.clear()
        editor?.commit()
        Log.e("AppSession" , "App session reset")
    }


    companion object {
        private const val PREF_AUTHENTICATED = "authState"
    }





}
