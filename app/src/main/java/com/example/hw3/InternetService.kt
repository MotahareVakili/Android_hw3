package com.example.hw3

import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class InternetService: Service() {

    // Initialize an empty list to store log entries

    private lateinit var internetReceiver: BroadcastReceiver
    override fun onCreate() {
        super.onCreate()

        //  broadcast receiver
         internetReceiver= object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {

                val cM =
                    context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = cM.activeNetworkInfo

                if (networkInfo != null && networkInfo.isConnected) {
                    sendNotification(context, "Internet Status", "Connected")
                    Log.v("HW3_part1", "Internet Connection: true")
                    Status.InternetStatus = "Internet Connected."
                } else {
                    sendNotification(context, "Internet Status", "Disconnected")
                    Log.v("HW3_part1", "Internet Connection: false")
                    Status.InternetStatus = "Internet Disconnected."
                }

                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDateAndTime: String = sdf.format(Date())
                val logObject = JSONObject()
                logObject.put("timestamp", currentDateAndTime)
                logObject.put("logType", "Internet")
                logObject.put("status", Status.InternetStatus)
                saveLogToFile(logObject)
            }

        }

        registerReceiver(internetReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetReceiver)
    }
    @SuppressLint("SuspiciousIndentation")
    private fun saveLogToFile(logObject: JSONObject = JSONObject()) {

        val logArray = JSONArray().put(logObject)
        val logFile = File(filesDir, "log.json")

            logFile.appendText(logArray.toString() )
            Log.d("HW3_part1", "Log entries saved to: ${logFile.absolutePath}")

        // Print contents of the log file to Logcat
            val reader = BufferedReader(FileReader(logFile))
            var line: String? = reader.readLine()
            while (line != null) {
                Log.d("HW3_part1", line)
                line = reader.readLine()
            }
            reader.close()
    }

}
