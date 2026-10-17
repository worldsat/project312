package com.example.project312.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Official Material 3 Dark ColorScheme for Nexus Habitat
private val NexusDarkColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = ElectricCyanDark,
    primaryContainer = ElectricCyanContainer,
    onPrimaryContainer = OnElectricCyanContainer,
    secondary = VioletAurora,
    onSecondary = Color.White,
    secondaryContainer = VioletContainer,
    onSecondaryContainer = OnVioletContainer,
    tertiary = EcoEmerald,
    onTertiary = OnEcoEmerald,
    tertiaryContainer = EcoEmeraldBright,
    onTertiaryContainer = OnEcoEmeraldContainer,
    background = ObsidianBackground,
    onBackground = OnBackgroundSlate50,
    surface = SurfaceObsidian,
    onSurface = OnSurfaceWhite,
    surfaceVariant = SurfaceContainerLow,
    onSurfaceVariant = OnSurfaceVariantSlate,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    outline = OutlineGlass,
    outlineVariant = OutlineVariantSlate,
    error = AlertCrimson
)

@Immutable
data class NexusGlassTokens(
    val glassCardGradient: Brush = Brush.verticalGradient(
        colors = listOf(
            SurfaceContainerLow.copy(alpha = 0.75f),
            SurfaceObsidian.copy(alpha = 0.85f)
        )
    ),
    val glassBorderGradient: Brush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.18f),
            Color.White.copy(alpha = 0.04f)
        )
    ),
    val cyanGlow: Color = ElectricCyanContainerGlow,
    val violetGlow: Color = VioletContainerGlow,
    val emeraldGlow: Color = EcoEmeraldContainer,
    val primaryGradient: Brush = Brush.horizontalGradient(
        colors = listOf(ElectricCyan, Color(0xFF00A3FF))
    ),
    val energyCurveGradient: Brush = Brush.horizontalGradient(
        colors = listOf(ElectricCyan, VioletContainerVibrant, EcoEmeraldBright)
    )
)

val LocalNexusGlassTokens = staticCompositionLocalOf { NexusGlassTokens() }

@Composable
fun NexusHabitatTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = NexusDarkColorScheme
    val glassTokens = NexusGlassTokens()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = false
                    isAppearanceLightNavigationBars = false
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalNexusGlassTokens provides glassTokens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Global accessor for custom glass styling
object NexusTheme {
    val glass: NexusGlassTokens
        @Composable
        get() = LocalNexusGlassTokens.current
}