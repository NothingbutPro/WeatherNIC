package com.parag.indianicweather.utils

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.location.LocationSettingsRequest
import com.parag.indianicweather.App


class GPSLocationViewModel : ViewModel() {
    private var mLocationRequest: com.google.android.gms.location.LocationRequest? = null
    var locationAuthentication = MutableLiveData<Location>()

    private val UPDATE_INTERVAL = (10 * 1000 /* 10 secs */).toLong()
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    fun getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        val locationClient = getFusedLocationProviderClient(App.context?.applicationContext!!)
        if (ActivityCompat.checkSelfPermission(
                App.context?.applicationContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                App.context?.applicationContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationClient.lastLocation
            .addOnSuccessListener { location -> // GPS location can be null if GPS is switched off
                location?.let {
                    locationAuthentication.postValue(it)

                }
            }
            .addOnFailureListener {
                Log.d("MapDemoActivity", "Error trying to get last GPS location")
                it.printStackTrace()
            }
    }

    fun gpsEnableLocation() {
        if (ActivityCompat.checkSelfPermission(
                App.context?.applicationContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                App.context?.applicationContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mLocationRequest = LocationRequest()
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest?.interval = UPDATE_INTERVAL
        mLocationRequest?.fastestInterval = FASTEST_INTERVAL

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)

//        val locationSettingsRequest = builder.build()
//        val settingsClient = LocationServices.getSettingsClient(MyApp.context?.applicationContext!!)
//        settingsClient.checkLocationSettings(locationSettingsRequest)


        getFusedLocationProviderClient(App.context?.applicationContext!!).requestLocationUpdates(
            mLocationRequest!!, mLocationCallback,
            Looper.myLooper()
        )

    }

    val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationAuthentication.postValue(locationResult.lastLocation)
            stop()
        }

    }

    fun stop() {
        getFusedLocationProviderClient(App.context?.applicationContext!!).removeLocationUpdates(
            mLocationCallback
        )

    }

}