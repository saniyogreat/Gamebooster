package com.saniyogreat.gamebooster

import android.app.ActivityManager
import android.content.Context
import android.os.BatteryManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saniyogreat.gamebooster.ui.theme.MyAppTheme

private val Background = Color(0xFF0B0E14)
private val CardBg = Color(0xFF151A24)
private val CardBgLight = Color(0xFF1C2230)
private val Accent = Color(0xFFC6FF00)
private val TextPrimary = Color(0xFFF0F2F5)
private val TextSecondary = Color(0xFF8B93A7)
private val TextMuted = Color(0xFF5C6578)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                GameboostApp()
            }
        }
    }
}

@Composable
fun GameboostApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar(containerColor = CardBg) {
                val items = listOf("Home", "Library", "Boost", "Perf", "Settings")
                val icons = listOf(
                    Icons.Outlined.Home to Icons.Filled.Home,
                    Icons.Outlined.List to Icons.Filled.List,
                    Icons.Outlined.Bolt to Icons.Filled.Bolt,
                    Icons.Outlined.Speed to Icons.Filled.Speed,
                    Icons.Outlined.Settings to Icons.Filled.Settings
                )

                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) icons[index].second else icons[index].first,
                                contentDescription = label
                            )
                        },
                        label = { Text(label, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Accent,
                            selectedTextColor = Accent,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = Accent.copy(alpha = 0.15f)
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { Toast.makeText(context, "⚡ Boost started!", Toast.LENGTH_SHORT).show() },
                containerColor = Accent,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Bolt, contentDescription = "Boost")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Background)
        ) {
            when (selectedTab) {
                0 -> HomeScreen()
                1 -> GameLibraryScreen()
                2 -> BoostFlowScreen()
                3 -> PerformanceScreen()
                4 -> SettingsScreen()
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Accent),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Bolt, null, tint = Color.Black, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(10.dp))
            Text("GAME", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("BOOST", color = Accent, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Icon(Icons.Default.Menu, null, tint = TextSecondary)
    }
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)

    val totalRam = memoryInfo.totalMem / (1024.0 * 1024 * 1024)
    val availableRam = memoryInfo.availMem / (1024.0 * 1024 * 1024)
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    val freeStorage = Environment.getDataDirectory().freeSpace / (1024.0 * 1024 * 1024)
    val totalStorage = Environment.getDataDirectory().totalSpace / (1024.0 * 1024 * 1024)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        TopBar()
        Spacer(Modifier.height(28.dp))
        Text("OVERVIEW / 01", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Ready when you are.", color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Your fast lane to a cleaner session.", color = TextSecondary, fontSize = 14.sp)
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { Toast.makeText(context, "⚡ Boost started!", Toast.LENGTH_SHORT).show() },
            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.height(48.dp)
        ) {
            Icon(Icons.Default.Bolt, null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Text("BOOST NOW", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(Modifier.weight(1f), Icons.Default.Memory, String.format("%.1f GB", availableRam), "RAM AVAILABLE", String.format("of %.1f GB", totalRam))
            StatCard(Modifier.weight(1f), Icons.Default.Storage, String.format("%.0f GB", freeStorage), "STORAGE FREE", String.format("of %.0f GB", totalStorage))
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(Modifier.weight(1f), Icons.Default.BatteryFull, "$battery%", "BATTERY", "device reading")
            StatCard(Modifier.weight(1f), Icons.Default.Star, "00", "GAME PROFILES", "ready to boost", true)
        }

        Spacer(Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = CardBgLight),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("SYSTEM PRIMED", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Text("One tap.", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Better match.", color = Accent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Simulate your full boost routine before your next ranked queue.", color = TextSecondary, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    sub: String,
    highlight: Boolean = false
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (highlight) Accent else CardBgLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = if (highlight) Color.Black else Accent, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.height(12.dp))
            Text(value, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(label, color = TextSecondary, fontSize = 11.sp)
            Text(sub, color = TextMuted, fontSize = 11.sp)
        }
    }
}

@Composable
fun GameLibraryScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        TopBar()
        Spacer(Modifier.height(24.dp))
        Text("LIBRARY / 02", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Your game loadout.", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("0 profiles tuned and ready.", color = TextSecondary, fontSize = 14.sp)
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { Toast.makeText(context, "Add Game coming soon", Toast.LENGTH_SHORT).show() },
            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("ADD GAME", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun BoostFlowScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        TopBar()
        Spacer(Modifier.height(24.dp))
        Text("BOOST FLOW / 03", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Clear the runway.", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { Toast.makeText(context, "⚡ Boost started!", Toast.LENGTH_SHORT).show() },
            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Icon(Icons.Default.Bolt, null)
            Spacer(Modifier.width(8.dp))
            Text("BOOST NOW", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun PerformanceScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        TopBar()
        Spacer(Modifier.height(24.dp))
        Text("TELEMETRY / 04", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Watch the signal.", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MiniStat(Modifier.weight(1f), "59", "FPS", Accent)
            MiniStat(Modifier.weight(1f), "16.9", "FRAME", Color(0xFF4FC3F7))
            MiniStat(Modifier.weight(1f), "42°", "THERMAL", Color(0xFFFFB74D))
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun MiniStat(modifier: Modifier, value: String, label: String, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardBg),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(value, color = color, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(label, color = TextMuted, fontSize = 11.sp)
        }
    }
}

@Composable
fun SettingsScreen() {
    var autoBoost by remember { mutableStateOf(false) }
    var haptics by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        TopBar()
        Spacer(Modifier.height(24.dp))
        Text("SYSTEM / 05", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Set your defaults.", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = CardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                SettingsToggle("Auto-boost", "Prepare a boost when you open the app", autoBoost) { autoBoost = it }
                SettingsToggle("Haptics", "Tactile feedback", haptics) { haptics = it }
                SettingsToggle("Notifications", "Show session updates", notifications) { notifications = it }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { Toast.makeText(context, "Data reset", Toast.LENGTH_SHORT).show() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D1515), contentColor = Color(0xFFFF6B6B)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("RESET ALL DATA")
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun SettingsToggle(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = TextPrimary, fontWeight = FontWeight.Medium)
            Text(subtitle, color = TextMuted, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = Accent,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = CardBgLight
            )
        )
    }
}
