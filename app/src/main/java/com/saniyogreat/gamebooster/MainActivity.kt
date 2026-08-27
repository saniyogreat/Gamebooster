package com.saniyogreat.gamebooster

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saniyogreat.gamebooster.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                GameboosterScreen(this)
            }
        }
    }
}

@Composable
fun GameboosterScreen(context: Context) {
    val activityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)

    val totalRam = memoryInfo.totalMem / (1024 * 1024 * 1024)
    val availableRam = memoryInfo.availMem / (1024 * 1024 * 1024)

    val totalStorage =
        Environment.getDataDirectory().totalSpace / (1024 * 1024 * 1024)

    val freeStorage =
        Environment.getDataDirectory().freeSpace / (1024 * 1024 * 1024)

    val batteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    val battery =
        batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "GAMEBOOSTER",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Optimize your gaming experience",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // Boost functionality will be added next.
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "⚡ BOOST GAME")
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                StatCard(
                    title = "RAM",
                    value = "$availableRam / $totalRam GB"
                )

                Spacer(modifier = Modifier.width(12.dp))

                StatCard(
                    title = "BATTERY",
                    value = "$battery%"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            StatCard(
                title = "STORAGE",
                value = "$freeStorage GB free / $totalStorage GB",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
