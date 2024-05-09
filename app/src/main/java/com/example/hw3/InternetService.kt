package com.example.hw3

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log

@Suppress("DEPRECATION")
class InternetService: Service() {
    private lateinit var internetReceiver: BroadcastReceiver
    override fun onCreate() {
        super.onCreate()

        // Register broadcast receiver
         internetReceiver= object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {

                val cM =
                    context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = cM.activeNetworkInfo

                if (networkInfo != null && networkInfo.isConnected) {
                    sendNotification(context, "Internet Status", "Connected")
                    Log.v("HW3", "Internet Connection: true")
                    Status.InternetStatus = "Internet Connected."
                } else {
                    sendNotification(context, "Internet Status", "Disconnected")
                    Log.v("HW3", "Internet Connection: false")
                    Status.InternetStatus = "Internet Disconnected."
                }
            }
        }

        registerReceiver(internetReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
