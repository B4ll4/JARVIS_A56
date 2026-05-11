package com.jarvis.a56

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jarvis.a56.ui.theme.JarvisTheme
import com.jarvis.a56.viewmodel.JarvisViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JarvisTheme {
                JarvisApp()
            }
        }
    }
}

@Composable
fun JarvisApp(viewModel: JarvisViewModel = viewModel()) {
    val isListening by viewModel.isListening.collectAsState()
    val systemStatus by viewModel.systemStatus.collectAsState()
    val batteryLevel by viewModel.batteryLevel.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0a0e27),
                        Color(0xFF1a1f3a)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Status Bar
            StatusBar(batteryLevel = batteryLevel, systemStatus = systemStatus)

            // Reator Arc (Central UI)
            ReactorArc(isListening = isListening, onClick = {
                viewModel.toggleListening()
            })

            // Command Panel
            CommandPanel(viewModel = viewModel)
        }
    }
}

@Composable
fun StatusBar(batteryLevel: Int, systemStatus: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "JARVIS A56",
            color = Color(0xFF00d9ff),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🔋 $batteryLevel%",
                color = Color(0xFFffd700),
                fontSize = 12.sp
            )
            Text(
                text = systemStatus,
                color = Color(0xFF00ff00),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ReactorArc(isListening: Boolean, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (isListening) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing)
        ),
        label = "reactor_rotation"
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF00d9ff).copy(alpha = 0.3f),
                        Color(0xFF0066cc).copy(alpha = 0.1f)
                    )
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Outer ring
        Box(
            modifier = Modifier
                .size(180.dp)
                .border(
                    width = 2.dp,
                    color = Color(0xFF00d9ff),
                    shape = CircleShape
                )
                .rotate(rotation)
        )

        // Middle ring
        Box(
            modifier = Modifier
                .size(140.dp)
                .border(
                    width = 1.5.dp,
                    color = Color(0xFF0099ff),
                    shape = CircleShape
                )
                .rotate(-rotation * 0.7f)
        )

        // Inner core
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFffd700),
                            Color(0xFFff6600)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isListening) "🎤" else "◉",
                fontSize = 40.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun CommandPanel(viewModel: JarvisViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Quick Commands
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickCommandButton("WiFi", onClick = { viewModel.toggleWiFi() })
            QuickCommandButton("BT", onClick = { viewModel.toggleBluetooth() })
            QuickCommandButton("GPS", onClick = { viewModel.toggleGPS() })
            QuickCommandButton("⚙️", onClick = { viewModel.openSettings() })
        }

        // Status Text
        Text(
            text = "Às suas ordens, Senhor",
            color = Color(0xFF00d9ff),
            fontSize = 14.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun QuickCommandButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1a3a52),
            contentColor = Color(0xFF00d9ff)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Border(
    modifier: Modifier = Modifier,
    width: Float,
    color: Color,
    shape: androidx.compose.foundation.shape.Shape
) {
    Box(
        modifier = modifier
            .border(width = width.dp, color = color, shape = shape)
    )
}
