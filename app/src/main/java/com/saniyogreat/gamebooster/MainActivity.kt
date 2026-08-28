package com.saniyogreat.gamebooster

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.saniyogreat.gamebooster.ui.theme.MyAppTheme

private val BackgroundTop = Color(0xFF080A12)
private val BackgroundBottom = Color(0xFF111827)
private val Panel = Color(0xFF171B28)
private val PanelLight = Color(0xFF202638)
private val Accent = Color(0xFFFF3D5A)
private val AccentDark = Color(0xFFB7193A)
private val TextPrimary = Color(0xFFF5F7FA)
private val TextSecondary = Color(0xFF9299AA)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyAppTheme {
                GameboostScreen()
            }
        }
    }
}

@Composable
fun GameboostScreen() {

    val context = LocalContext.current

    // REAL RAM INFORMATION
    val activityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)

    val totalRamGb =
        memoryInfo.totalMem / (1024.0 * 1024.0 * 1024.0)

    val availableRamGb =
        memoryInfo.availMem / (1024.0 * 1024.0 * 1024.0)

    // REAL BATTERY INFORMATION
    val batteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    val batteryPercent =
        batteryManager.getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )

    // REAL STORAGE INFORMATION
    val dataDirectory = Environment.getDataDirectory()

    val freeStorageGb =
        dataDirectory.freeSpace / (1024.0 * 1024.0 * 1024.0)

    Scaffold(
        containerColor = BackgroundTop
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            BackgroundTop,
                            BackgroundBottom
                        )
                    )
                )
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    )
            ) {

                Header()

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                DeviceStatus()

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                BoostButton()

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Text(
                    text = "DEVICE STATUS",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                StatsGrid(
                    availableRam = availableRamGb,
                    totalRam = totalRamGb,
                    battery = batteryPercent,
                    freeStorage = freeStorageGb
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "GAME CENTER",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                GameCard()
            }
        }
    }
}

@Composable
private fun Header() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Text(
                text = "GAMEBOOST",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "DEVICE COMMAND CENTER",
                color = TextSecondary,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }

        Surface(
            modifier = Modifier.size(44.dp),
            shape = CircleShape,
            color = Panel
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "⚙",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
private fun DeviceStatus() {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Color(0xFF42E57A))
        )

        Spacer(
            modifier = Modifier.width(9.dp)
        )

        Text(
            text = "DEVICE READY",
            color = TextPrimary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = "•",
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = "PERFORMANCE MODE",
            color = TextSecondary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun BoostButton() {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier.size(190.dp),
            shape = CircleShape,
            color = AccentDark
        ) {

            Surface(
                modifier = Modifier
                    .padding(6.dp)
                    .size(178.dp),
                shape = CircleShape,
                color = Accent
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "⚡",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "BOOST",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "TAP TO OPTIMIZE",
                        color = Color.White.copy(alpha = 0.75f),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsGrid(
    availableRam: Double,
    totalRam: Double,
    battery: Int,
    freeStorage: Double
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            StatCard(
                modifier = Modifier.weight(1f),
                symbol = "RAM",
                title = "MEMORY",
                value = String.format(
                    "%.1f / %.1f GB",
                    availableRam,
                    totalRam
                )
            )

            StatCard(
                modifier = Modifier.weight(1f),
                symbol = "CPU",
                title = "PROCESSOR",
                value = "READY"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            StatCard(
                modifier = Modifier.weight(1f),
                symbol = "⚡",
                title = "BATTERY",
                value = "$battery%"
            )

            StatCard(
                modifier = Modifier.weight(1f),
                symbol = "▣",
                title = "STORAGE",
                value = String.format(
                    "%.0f GB FREE",
                    freeStorage
                )
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    symbol: String,
    title: String,
    value: String
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Panel
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = symbol,
                color = Accent,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = title,
                color = TextSecondary,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = value,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GameCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = PanelLight
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(14.dp),
                color = Accent.copy(alpha = 0.15f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "🎮",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "MY GAMES",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Select a game to optimize",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = "›",
                color = Accent,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

