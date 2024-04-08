package com.parag.indianicweather

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.instacart.library.truetime.TrueTimeRx
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.schedulers.Schedulers
import java.util.Date


@HiltAndroidApp
class App : Application() {
    companion object {

        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        context = this


    }




}