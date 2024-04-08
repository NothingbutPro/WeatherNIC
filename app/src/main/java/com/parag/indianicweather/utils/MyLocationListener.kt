package com.parag.indianicweather.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.Locale


 class MyLocationListener(var context :Context) : LocationListener {
    private val TAG: String? = "MyLocationListener"

    override fun onLocationChanged(loc: Location) {

        Toast.makeText(context, ("Location changed: Lat: " + loc.getLatitude()).toString() + " Lng: " + loc.longitude, Toast.LENGTH_SHORT).show()
        val longitude = "Longitude: " + loc.getLongitude()
        Log.v(TAG, longitude)
        val latitude = "Latitude: " + loc.getLatitude()
        Log.v(TAG, latitude)

        /*------- To get city name from coordinates -------- */
        var cityName: String? = null
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = gcd.getFromLocation(
                loc.getLatitude(),
                loc.getLongitude(), 1
            )
            if (addresses!!.size > 0) {
                System.out.println(addresses[0].locality)
                cityName = addresses[0].getLocality()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val s = (longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName)

    }

//    fun onProviderDisabled(provider: String?) {}
//    fun onProviderEnabled(provider: String?) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {


    }
}