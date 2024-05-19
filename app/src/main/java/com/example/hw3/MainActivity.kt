package com.example.hw3
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.hw3.ui.theme.HW3Theme
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serviceIntent = Intent(this, InternetService::class.java)
        startService(serviceIntent)

        setContent {
            HW3Theme {

                // A surface container using the 'background' color from the theme

                HW3_App(this)

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

@Composable
fun HW3_App(context: Context) {
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = Status.InternetStatus, fontSize = 20.sp, color= Color.Magenta)
        Spacer(modifier = Modifier.height(12.dp))
        val logs = remember { readLogsFromFile(context) }

        LazyColumn {
            items(logs) { log ->
                LogItem(log)
            }
        }

    }
}

@Composable
fun LogItem(log: JSONObject) {
    val timestamp = log.getString("timestamp")
    val logType = log.getString("logType")
    val status = log.getString("status")

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(text = "Timestamp: $timestamp")
        Text(text = "Type: $logType")
        Text(text = "Status: $status")
        Divider(color = Color.Black, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
    }

}

fun readLogsFromFile(context: Context): List<JSONObject> {
    val logFile = File(context.filesDir, "log.json")
    if (!logFile.exists()) return emptyList()

    val logArray = JSONArray(logFile.readText())
    val logList = mutableListOf<JSONObject>()

    for (i in 0 until logArray.length()) {
        logList.add(logArray.getJSONObject(i))
    }

    // معکوس کردن ترتیب لاگ‌ها
    return logList.asReversed()
}

