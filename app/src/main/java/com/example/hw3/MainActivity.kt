package com.example.hw3
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hw3.ui.theme.HW3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val serviceIntent = Intent(this, InternetService::class.java)
        startService(serviceIntent)

        setContent {
            HW3Theme {

                // A surface container using the 'background' color from the theme

                HW3_App()

            }
          
        }
    }
}



@Composable
fun HW3_App() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Hello ^_^ ", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = Status.InternetStatus, fontSize = 20.sp, color= Color.Magenta)
    }
}

