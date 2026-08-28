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
import androidx.compose.foundation.clickable
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

// Colors matching the Replit design
private val Background = Color(0xFF0B0E14)
private val CardBg = Color(0xFF151A24)
private val CardBgLight = Color(0xFF1C2230)
private val Accent = Color(0xFFC6FF00)          // Neon lime green
private val AccentDark = Color(0xFF9ACC00)
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
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "⚡ Boost started!", Toast.LENGTH_SHORT).show()
                },
                containerColor = Accent,
                contentColor = Color.Black,
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("BOOST", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
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

// ==================== BOTTOM NAV ====================
@Composable
fun BottomNavBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val items = listOf(
        NavItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
        NavItem("Game library", Icons.Outlined.SportsEsports, Icons.Filled.SportsEsports),
        NavItem("Boost flow", Icons.Outlined.Bolt, Icons.Filled.Bolt),
        NavItem("Performance", Icons.Outlined.Speed, Icons.Filled.Speed),
        NavItem("Settings", Icons.Outlined.Settings, Icons.Filled.Settings)
    )

    NavigationBar(
        containerColor = CardBg,
        contentColor = TextPrimary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == index) item.selectedIcon else item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label, fontSize = 10.sp) },
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
}

data class NavItem(val label: String, val icon: ImageVector, val selectedIcon: ImageVector)

// ==================== HOME SCREEN ====================
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    // Real system values
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
        // Top bar
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

        Spacer(Modifier.height(28.dp))

        Text("OVERVIEW / 01", color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Ready when you are.", color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(
            "Your fast lane to a cleaner session. Pick a profile, hit boost, get in.",
            color = TextSecondary,
            fontSize = 14.sp
        )

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

        // Stats grid
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Memory,
                value = String.format("%.1f GB", availableRam),
                label = "RAM AVAILABLE",
                sub = String.format("of %.1f GB", totalRam)
            )
            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Storage,
                value = String.format("%.0f GB", freeStorage),
                label = "STORAGE FREE",
                sub = String.format("of %.0f GB", totalStorage)
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.BatteryFull,
                value = "$battery%",
                label = "BATTERY",
                sub = "device reading"
            )
            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.SportsEsports,
                value = "00",
                label = "GAME PROFILES",
                sub = "ready to boost",
                highlight = true
            )
        }

        Spacer(Modifier.height(24.dp))

        // Hero card
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBgLight),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Surface(
                    color = Accent.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "SYSTEM PRIMED",
                        color = Accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text("One tap.", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Better match.", color = Accent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(
                    "Simulate your full boost routine before your next ranked queue.",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
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
                Icon(
                    icon,
                    null,
                    tint = if (highlight) Color.Black else Accent,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(value, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(2.dp))
            Text(label, color = TextSecondary, fontSize = 11.sp)
            Text(sub, color = TextMuted, fontSize = 11.sp)
        }
    }
}

// ==================== GAME LIBRARY ====================
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
        Text("0 profiles tuned and ready. Each game gets its own competitive baseline.", color = TextSecondary, fontSize = 14.sp)

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

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search games...", color = TextMuted) },
            leadingIcon = { Icon(Icons.Default.Search, null, tint = TextMuted) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = CardBgLight,
                focusedBorderColor = Accent,
                unfocusedContainerColor = CardBg,
                focusedContainerColor = CardBg
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))
        Text("0 / 0 PROFILES", color = TextMuted, fontSize = 12.sp)

        Spacer(Modifier.height(12.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No profiles match that search.", color = TextPrimary, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(6.dp))
                Text("Try a different title or add a new loadout.", color = TextSecondary, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

// ==================== BOOST FLOW ====================
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
        Spacer(Modifier.height(6.dp))
        Text("A six-step pre-match sweep for your selected profile.", color = TextSecondary, fontSize = 14.sp)

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

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { Toast.makeText(context, "Boost & Play coming soon", Toast.LENGTH_SHORT).show() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
        ) {
            Icon(Icons.Default.PlayArrow, null)
            Spacer(Modifier.width(8.dp))
            Text("BOOST & PLAY", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))

        Text("Boost sequence", color = TextPrimary, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        val steps = listOf(
            "Scanning device" to "Reading available device signals",
            "Checking background apps" to "Identifying memory pressure",
            "Loading game profile" to "Applying maximum preset",
            "Checking performance mode" to "Verifying device headroom",
            "Preparing game" to "Building a clean launch state",
            "Launching game" to "Ready when Native Android is connected"
        )

        steps.forEachIndexed { index, (title, desc) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(CardBgLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${index + 1}", color = TextSecondary, fontSize = 12.sp)
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(title, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Text(desc, color = TextMuted, fontSize = 12.sp)
                }
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

// ==================== PERFORMANCE ====================
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
        Spacer(Modifier.height(6.dp))
        Text("Live performance surface for your device.", color = TextSecondary, fontSize = 14.sp)

        Spacer(Modifier.height(20.dp))

        // Fake graph placeholder
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBg),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().height(140.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("LIVE GRAPH (coming soon)", color = TextMuted)
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MiniStat(Modifier.weight(1f), "59", "FPS", Accent)
            MiniStat(Modifier.weight(1f), "16.9", "FRAME TIME", Color(0xFF4FC3F7))
            MiniStat(Modifier.weight(1f), "42°", "THERMAL", Color(0xFFFFB74D))
        }

        Spacer(Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor
