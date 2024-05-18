package com.example.hw3.ui.theme

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class StatusWorker(private val appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Check Bluetooth status
        val isBluetoothEnabled = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.BLUETOOTH_ON,
            0
        ) == 1

        // Check Airplane mode status
        val isAirplaneModeEnabled = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) == 1

        // Log the status
        val bluetoothStatus = if (isBluetoothEnabled) "enabled" else "disabled"
        val airplaneModeStatus = if (isAirplaneModeEnabled) "enabled" else "disabled"
        Log.i("worker_bluetooth", "Bluetooth is $bluetoothStatus")
        Log.i("worker_airplane", "Airplane mode is $airplaneModeStatus")

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDateAndTime: String = sdf.format(Date())
        val logObject_a = JSONObject()
        logObject_a.put("timestamp", currentDateAndTime)
        logObject_a.put("logType", "Airplane")
        logObject_a.put("status", airplaneModeStatus)
        saveLogToFile(appContext,logObject_a)

        val logObject_b = JSONObject()
        logObject_b.put("timestamp", currentDateAndTime)
        logObject_b.put("logType", "Bluetooth")
        logObject_b.put("status", bluetoothStatus)
        saveLogToFile(appContext,logObject_b)

        return Result.success()

    }
    private fun saveLogToFile(Context: Context,logObject: JSONObject = JSONObject()) {

        val logArray = JSONArray().put(logObject)
        val logFile = File(Context.filesDir , "log.json")

        logFile.appendText(logArray.toString() )
        Log.d("status_path", "Log entries saved to: ${logFile.absolutePath}")

        // Print contents of the log file to Logcat
        val reader = BufferedReader(FileReader(logFile))
        var line: String? = reader.readLine()
        while (line != null) {
            Log.d("status", line)
            line = reader.readLine()
        }
        reader.close()

    }
}