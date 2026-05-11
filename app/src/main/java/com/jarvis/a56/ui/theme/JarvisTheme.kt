package com.jarvis.a56.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00d9ff),
    secondary = Color(0xFFffd700),
    tertiary = Color(0xFF00ff00),
    background = Color(0xFF0a0e27),
    surface = Color(0xFF1a1f3a),
    error = Color(0xFFff4444)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0066cc),
    secondary = Color(0xFFff9900),
    tertiary = Color(0xFF00aa00),
    background = Color(0xFFffffff),
    surface = Color(0xFFf5f5f5),
    error = Color(0xFFcc0000)
)

@Composable
fun JarvisTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
