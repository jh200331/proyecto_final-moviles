package com.example.proyectofinal_moviles.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EseitLightColorScheme = lightColorScheme(
    primary = EseitNavy,
    onPrimary = EseitWhite,
    primaryContainer = EseitNavyDark,
    onPrimaryContainer = EseitWhite,
    secondary = EseitRed,
    onSecondary = EseitWhite,
    tertiary = EseitTeal,
    background = EseitBackground,
    onBackground = EseitOnSurface,
    surface = EseitSurface,
    onSurface = EseitOnSurface,
    surfaceVariant = Color(0xFFE8EAED),
    onSurfaceVariant = EseitMuted,
    outline = Color(0xFFE0E4EA)
)

private val EseitDarkColorScheme = darkColorScheme(
    primary = Color(0xFF3D5A8A),
    onPrimary = EseitWhite,
    primaryContainer = EseitNavyDark,
    onPrimaryContainer = EseitWhite,
    secondary = Color(0xFFEF5350),
    onSecondary = EseitWhite,
    tertiary = EseitTeal,
    background = EseitDarkBackground,
    onBackground = EseitDarkOnSurface,
    surface = EseitDarkSurface,
    onSurface = EseitDarkOnSurface,
    surfaceVariant = Color(0xFF1A2F5C),
    onSurfaceVariant = Color(0xFF9AA3B5),
    outline = Color(0xFF1A2F5C)
)

@Composable
fun ProyectoFinal_movilesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EseitDarkColorScheme else EseitLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
