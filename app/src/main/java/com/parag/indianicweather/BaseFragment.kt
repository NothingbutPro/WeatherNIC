package com.parag.indianicweather


import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation


open class BaseFragment : Fragment() {
    var alertDialog: AlertDialog? = null
    private var navController: NavController? = null
    /**
     * initializeNavigation
     *
     * @param requireActivity
     */
    fun initializeNavigation(requireActivity: FragmentActivity) {
        navController = Navigation.findNavController(requireActivity, R.id.nav_host_fragment)

    }



    fun showLoading() {
//        if (!isVisible) {
            if (!alertDialog!!.isShowing) {
                alertDialog!!.show()
            }
//        }
    }

    fun hideLoading() {
        try {
            if (alertDialog!!.isShowing || alertDialog != null) {
                Log.e("Call", "Call " + alertDialog!!.isShowing + " " + (alertDialog != null))
                alertDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Navigate to screen
     *
     * @param actionNav
     * @param bundle
     */
    fun navigateToScreen(actionNav: Int, bundle: Bundle?) {
        navController!!.navigate(actionNav, bundle)
    }
//
}