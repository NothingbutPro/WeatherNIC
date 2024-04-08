package com.parag.indianicweather.utils

import android.Manifest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.parag.indianicweather.R
import java.util.concurrent.Executor


class LocationWorker (var ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    var TAG = "LocationWorker"
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx)
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                Log.e(TAG ,""+location.latitude)
                Log.e(TAG ,""+location.longitude)
                val sydney = LatLng(location.latitude, location.longitude)
//                showNotificationWithRawSound(ctx,"SHowiwithID" ,"WithSound",1 ,"SOundTest" ,"Sound" ,R.raw.notification_test)
                // Handle the updated location
                // location.latitude and location.longitude provide the coordinates
            }
        }
    }
    override fun doWork(): Result {
        Log.e("getLocationUpdates" , "doWork")
        // Handle the work (e.g., request location updates)
        requestLocationUpdates()

        // Indicate that the work is successful
        return Result.success()
    }

    private fun requestLocationUpdates() {
        Log.e(TAG ,"requestLocationUpdates" )
        if (ActivityCompat.checkSelfPermission(
                ctx,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        WorkManager.getInstance(ctx).getWorkInfosForUniqueWork("sendLoc").addListener(object :Runnable{
            override fun run() {
                Log.e(TAG , "getWorkInfosForUniqueWork run")
            }

        }, Executor {
            Log.e(TAG , "Executor run")
        })
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//            Log.e(TAG ,"lastLocation "+it.latitude)
//            Log.e(TAG ,"lastLocation "+it.longitude)
//        }


    }

    fun showNotificationWithRawSound(context: Context, channelId: String, channelName: String, channelImportance: Int, title: String, message: String, rawSoundResource: Int) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.e(TAG ,"showNotificationWithRawSound start" )
        // Create a notification channel for devices running Android 8.0 (Oreo) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.lightColor = context.resources.getColor(R.color.background_color_0a0d1e)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_proceed_next)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Set the notification sound from the raw resource
        val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.lumos)
        Log.e(TAG ,"android.resource:// start" )
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification: Notification = builder.build()
//        notification.sound = Uri.parse(
//            ("android.resource://"
//                    + context.packageName).toString() + "/" + com.paragsharma.indianicweather.R.raw.lumos
//        )
        builder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +  context.packageName+ "/raw/lumos"))
//        builder.setSound(soundUri, R.raw.lumos)

        // Show the notification
        notificationManager.notify(1, builder.build())
        Log.e(TAG ,"showNotificationWithRawSound End" )
    }

    override fun onStopped() {
        Log.e(TAG ,"onStopped" )
        fusedLocationClient.removeLocationUpdates(locationCallback)

        super.onStopped()
    }
}