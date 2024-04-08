package com.parag.indianicweather.utils

import android.Manifest
import android.os.Build

/**
 * App constant
 *
 * @constructor Create empty App constant
 */
class AppConstant {

    companion object{

        val GPS_REQUEST: Int? = 100
        const val SEE_ALL_TYPE: String = "seealltype"
        const val SEE_PRODUCT_TITLE: String = "seeproducttitle"
        const  val APIKEY: String = "AIzaSyDW3dxC_aRqaUf511e533AjnQUL0X"

        //+++++++++++++++++++++++++++++Permissions++++++++++++++++++++++++++++++

        val LOCATION_PERMISSIONS_REQUIRED = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
        val LOCATION_PERMISSIONS_BACKGROUND_REQUIRED = arrayOf<String>(Manifest.permission.ACCESS_BACKGROUND_LOCATION)



    }
}