package com.parag.indianicweather.onboarding

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.parag.indianicweather.R
import com.parag.indianicweather.databinding.ActivityWelcomeBinding
import com.parag.indianicweather.onboarding.viewmodels.OnBoardingViewModel
import com.parag.indianicweather.utils.AppConstant
import com.parag.indianicweather.utils.AppSession
import com.parag.indianicweather.utils.GPSCheck
import com.parag.indianicweather.utils.GPSLocationViewModel

import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in AppConstant.LOCATION_PERMISSIONS_REQUIRED && !it.value)
                    permissionGranted = false
            }

            if (!permissionGranted) {
                Toast.makeText(this, "Location Permission request denied", Toast.LENGTH_SHORT).show()

            } else {
                startGettingLocation()

            }
        }
    @Inject
    lateinit var appSession: AppSession
    val gpsLocationViewModel: GPSLocationViewModel by viewModels()
    val viewModel : OnBoardingViewModel by viewModels()

    fun getWeatherViewModel(): OnBoardingViewModel {
        return viewModel
    }

    fun hasGpsPermissions(): Boolean {
        val manager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener{
                _, _, _ ->
        }

        gpsLocationViewModel.locationAuthentication.observe(this) {
            val addresses: List<Address>?
            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
            addresses = geocoder.getFromLocation(
                it.latitude,
                it.longitude,
                1
            )

            val address = addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


        }

        registerReceiver(GPSCheck(object : GPSCheck.LocationCallBack {
            override fun turnedOn() {
                checkPermission();

            }

            override fun turnedOff() {
         Toast.makeText(this@WelcomeActivity ,"GPS Off" ,Toast.LENGTH_LONG).show()
            }
        }), IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))

        checkPermission();
    }

    /**
     * Checkpermission
     *
     */
    fun checkPermission() {
        if (!hasGpsPermissions()) {
        } else {
            if (hasPermissions(this)) {
                startGettingGpsLocation()
            } else {
                activityResultLauncher.launch(AppConstant.LOCATION_PERMISSIONS_REQUIRED)
            }
        }
    }

    /**
     * Has permissions
     *
     * @param context
     */
    fun hasPermissions(context: Context) = AppConstant.LOCATION_PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onResume() {
        super.onResume()
    }


    fun startGettingLocation() {
        gpsLocationViewModel.getLastLocation()

    }

    fun startGettingGpsLocation() {
        gpsLocationViewModel.gpsEnableLocation()

    }
}