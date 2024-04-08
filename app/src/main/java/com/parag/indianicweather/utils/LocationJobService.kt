package com.parag.indianicweather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.parag.indianicweather.R
import com.parag.indianicweather.onboarding.WelcomeActivity

@SuppressLint("SpecifyJobSchedulerIdRange")
class LocationJobService : JobService() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onStartJob(params: JobParameters?): Boolean {
        // Handle the work here
        onHandleWork()
        jobFinished(params, false) // Indicates that the work is completed
        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return false
    }

    override fun onCreate() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        super.onCreate()
        // Your initialization code
    }

    private fun onHandleWork(){
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000 ).apply {
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
//                    Log.e("getLocationUpdates" ,""+location.latitude)
//                    Log.e("getLocationUpdates" ,""+location.longitude)
                    val sydney = LatLng(location.latitude, location.longitude)

                    // Handle the updated location
                    // location.latitude and location.longitude provide the coordinates
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Start your background tasks here

        // Create a notification to make this a Foreground Service
        val notification = createNotification()
        startForeground(1, notification)

        // Return START_STICKY to ensure the service restarts if it's killed by the system
        return START_STICKY
    }

    private fun createNotification(): Notification {
        // Create a Notification to run the service in the foreground
        val notificationChannelId = "LocationJobServiceChannel"
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Project A")
            .setContentText("Project Getting Location")
            .setSmallIcon(R.drawable.ic_proceed_next)

        val notificationIntent = Intent(this, WelcomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder.setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return notificationBuilder.build()
    }



}
