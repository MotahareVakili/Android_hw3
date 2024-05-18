package com.example.hw3
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.hw3.ui.theme.HW3Theme
import com.example.hw3.ui.theme.StatusWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HW3Theme {
                // A surface container using the 'background' color from the theme
            }
            startPeriodicWorker(this)
        }
    }
}

fun startPeriodicWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
        .build()

    val statusCheckRequest = PeriodicWorkRequestBuilder<StatusWorker>(15, TimeUnit.MINUTES)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "StatusWorker",
        ExistingPeriodicWorkPolicy.REPLACE,
        statusCheckRequest
    )
}


