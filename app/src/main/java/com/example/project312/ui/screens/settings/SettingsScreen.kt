package com.example.project312.ui.screens.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.CloudSync
import androidx.compose.material.icons.rounded.DeviceHub
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.MotionPhotosAuto
import androidx.compose.material.icons.rounded.NearbyError
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Radar
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.Vibration
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.WifiTethering
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.CyberThemeAccent
import com.example.project312.model.HabitatCoreInfo
import com.example.project312.model.QuickActionAccent
import com.example.project312.model.SettingsUiAction
import com.example.project312.model.SettingsUiState
import com.example.project312.model.TemperatureUnit
import com.example.project312.ui.components.GlassContainer
import com.example.project312.ui.components.NeonPillSwitch
import com.example.project312.ui.theme.AlertCrimson
import com.example.project312.ui.theme.EcoEmerald
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.EcoEmeraldDim
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.SurfaceContainerLowest
import com.example.project312.ui.theme.VioletAurora
import com.example.project312.ui.theme.VioletContainer
import com.example.project312.ui.theme.VioletContainerVibrant

/**
 * SettingsScreen: High-fidelity futuristic smart home configuration hub for Nexus Habitat.
 * Follows Obsidian Dark aesthetic, cyber glassmorphism, and neon telemetry design tokens.
 * 1:1 synchronization with Flutter settings_screen.dart.
 */
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeAccentColor = when (state.display.selectedAccent) {
        CyberThemeAccent.ELECTRIC_CYAN -> ElectricCyan
        CyberThemeAccent.VIOLET_AURORA -> VioletAurora
        CyberThemeAccent.ECO_EMERALD -> EcoEmerald
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Ambient Glow Backdrops
        if (state.display.ambientBackdropGlows) {
            SettingsAmbientBackdrops(accentColor = activeAccentColor)
        }

        // 2. Main Scrollable List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                SettingsHeader(firmwareVersion = state.coreInfo.firmwareVersion)
            }

            // Hub Core Architecture Telemetry Card
            item {
                HabitatCoreStatusCard(
                    coreInfo = state.coreInfo,
                    accentColor = activeAccentColor
                )
            }

            // Section: Mesh & Protocol Connectivity
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle(
                        title = "MESH & PROTOCOL CONNECTIVITY",
                        icon = Icons.Rounded.Hub,
                        tint = ElectricCyan
                    )
                    ConnectivityCard(
                        state = state,
                        onAction = onAction
                    )
                }
            }

            // Section: Automation & Intelligence
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle(
                        title = "AUTOMATION & INTELLIGENCE",
                        icon = Icons.Rounded.AutoAwesome,
                        tint = VioletAurora
                    )
                    AutomationCard(
                        state = state,
                        onAction = onAction
                    )
                }
            }

            // Section: Interface & Cyber Aesthetics
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle(
                        title = "INTERFACE & CYBER AESTHETICS",
                        icon = Icons.Rounded.Palette,
                        tint = EcoEmeraldDim
                    )
                    InterfaceCard(
                        state = state,
                        onAction = onAction
                    )
                }
            }

            // Section: Grid Security & Diagnostics
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SectionTitle(
                        title = "GRID SECURITY & DIAGNOSTICS",
                        icon = Icons.Rounded.Shield,
                        tint = AlertCrimson
                    )
                    SecurityCard(
                        state = state,
                        onAction = onAction
                    )
                }
            }

            // System Version Watermark
            item {
                SettingsWatermark()
            }
        }

        // 3. Local Feedback Floating Toast Overlay
        AnimatedVisibility(
            visible = state.userFeedback != null,
            enter = fadeIn(animationSpec = tween(200)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(250)
            ),
            exit = fadeOut(animationSpec = tween(150)) + slideOutVertically(
                targetOffsetY = { it / 2 },
                animationSpec = tween(200)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        ) {
            state.userFeedback?.let { feedback ->
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = Color.Black.copy(alpha = 0.5f),
                            spotColor = Color.Black.copy(alpha = 0.5f)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHighest)
                        .border(
                            width = 1.dp,
                            color = ElectricCyan.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = feedback,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                color = OnBackgroundSlate50
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(color = ElectricCyan),
                                    onClick = { onAction(SettingsUiAction.ClearFeedback) }
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "OK",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricCyan
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Ambient colored glow accents behind the settings screen.
 * Matches Flutter positioned glows (Top-Start -30,-20 and Top-End -40,300).
 */
@Composable
private fun BoxScope.SettingsAmbientBackdrops(accentColor: Color) {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .offset(x = (-30).dp, y = (-20).dp)
            .size(240.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accentColor.copy(alpha = 0.18f),
                        accentColor.copy(alpha = 0.04f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(x = 40.dp, y = 300.dp)
            .size(260.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        VioletContainerVibrant.copy(alpha = 0.20f),
                        VioletContainerVibrant.copy(alpha = 0.05f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
}

/**
 * Settings Header.
 */
@Composable
private fun SettingsHeader(firmwareVersion: String) {
    Column {
        Text(
            text = "Habitat Matrix",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = OnBackgroundSlate50
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Node 01 // Firmware $firmwareVersion • Mesh Secure",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 12.sp,
                color = OnSurfaceVariantSlate
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Hub Core Architecture Telemetry Card.
 */
@Composable
private fun HabitatCoreStatusCard(
    coreInfo: HabitatCoreInfo,
    accentColor: Color
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(accentColor.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Memory,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = coreInfo.hubId,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnBackgroundSlate50
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(EcoEmeraldBright.copy(alpha = 0.15f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "ONLINE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EcoEmeraldDim
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${coreInfo.meshNodesConnected} Nodes Synched • ${coreInfo.networkLatencyMs} ms Latency",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = OnSurfaceVariantSlate
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Security Tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.70f))
                        .border(1.dp, OutlineGlass.copy(alpha = 0.15f), RoundedCornerShape(999.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Security,
                            contentDescription = null,
                            tint = ElectricCyanFixedDim,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = coreInfo.securityLevel.encryption,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ElectricCyanFixedDim
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3-Metric Snapshot Strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.60f))
                    .padding(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${coreInfo.hubHealthPercent}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = EcoEmeraldDim
                            )
                        )
                        Text(
                            text = "Hub Health",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${coreInfo.networkLatencyMs} ms",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = ElectricCyan
                            )
                        )
                        Text(
                            text = "RF Latency",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${coreInfo.uptimeDays}d",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VioletAurora
                            )
                        )
                        Text(
                            text = "Total Uptime",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Section Title with glowing neon icon.
 */
@Composable
private fun SectionTitle(
    title: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.padding(start = 2.dp, top = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                letterSpacing = 1.sp,
                color = tint
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/**
 * Connectivity Card.
 */
@Composable
private fun ConnectivityCard(
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column {
            SettingSwitchRow(
                icon = Icons.Rounded.WifiTethering,
                title = "Thread Mesh Protocol (6LoWPAN)",
                subtitle = "Multi-hop, low-latency, self-healing wireless grid",
                checked = state.connectivity.threadMeshActive,
                accent = QuickActionAccent.CYAN,
                onCheckedChange = { onAction(SettingsUiAction.ToggleThreadMesh(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.Psychology,
                title = "Local AI Neural Inference",
                subtitle = "Edge coprocessor runs routines with 0ms cloud lag",
                checked = state.connectivity.localAiEdgeInference,
                accent = QuickActionAccent.PURPLE,
                onCheckedChange = { onAction(SettingsUiAction.ToggleLocalAi(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.DeviceHub,
                title = "Universal Matter Bridge",
                subtitle = "Matter 1.3 sync for Apple Home & Google Home",
                checked = state.connectivity.matterBridgeSync,
                accent = QuickActionAccent.EMERALD,
                onCheckedChange = { onAction(SettingsUiAction.ToggleMatterBridge(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.CloudSync,
                title = "Encrypted Cloud Snapshot Relay",
                subtitle = "Zero-knowledge offsite backup of habitat state",
                checked = state.connectivity.cloudTelemetrySync,
                accent = QuickActionAccent.CYAN,
                onCheckedChange = { onAction(SettingsUiAction.ToggleCloudSync(it)) }
            )
        }
    }
}

/**
 * Automation Card.
 */
@Composable
private fun AutomationCard(
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column {
            SettingSwitchRow(
                icon = Icons.Rounded.MotionPhotosAuto,
                title = "Autonomous Eco-Tuning",
                subtitle = "AI dynamically modulates heat pumps and loads",
                checked = state.automation.autonomousEcoTuning,
                accent = QuickActionAccent.EMERALD,
                onCheckedChange = { onAction(SettingsUiAction.ToggleAutonomousEco(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.NearbyError,
                title = "500m Geofence Perimeter Auto-Arm",
                subtitle = "Pre-cools habitat and locks nodes upon exit",
                checked = state.automation.geofenceAutoArm,
                accent = QuickActionAccent.CYAN,
                onCheckedChange = { onAction(SettingsUiAction.ToggleGeofenceAutoArm(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.Radar,
                title = "60GHz mmWave Presence Radar",
                subtitle = "Micro-motion breath sensing avoids false-offs",
                checked = state.automation.millimeterPresence,
                accent = QuickActionAccent.PURPLE,
                onCheckedChange = { onAction(SettingsUiAction.ToggleMillimeterPresence(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.WbSunny,
                title = "Circadian Lighting Spectrum",
                subtitle = "Auto daylight Kelvin sync (2700K - 6500K)",
                checked = state.automation.circadianSpectrumLighting,
                accent = QuickActionAccent.CYAN,
                onCheckedChange = { onAction(SettingsUiAction.ToggleCircadianLighting(it)) }
            )

            SettingsDivider()

            // Temperature Unit Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ElectricCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Thermostat,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Thermal Telemetry Unit",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = "Current: ${state.automation.tempUnit.label} (${state.automation.tempUnit.symbol})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = OnSurfaceVariantSlate
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Temperature Unit Toggle Pills
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowest)
                        .border(1.dp, OutlineGlass.copy(alpha = 0.12f), RoundedCornerShape(999.dp))
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TemperatureUnit.entries.forEach { unit ->
                        val isSelected = unit == state.automation.tempUnit
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(
                                    if (isSelected) ElectricCyan.copy(alpha = 0.25f)
                                    else Color.Transparent
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { onAction(SettingsUiAction.SelectTempUnit(unit)) }
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = unit.symbol,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) ElectricCyan else OnSurfaceVariantSlate
                                )
                            )
                        }
                    }
                }
            }

            SettingsDivider()

            // AQI Alert Threshold Slider
            Column(horizontalAlignment = Alignment.Start) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Air,
                            contentDescription = null,
                            tint = EcoEmeraldDim,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "AQI Alert Trigger",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                    }
                    Text(
                        text = "> ${state.automation.aqiAlertThreshold} AQI",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = EcoEmeraldBright
                        )
                    )
                }

                Slider(
                    value = state.automation.aqiAlertThreshold.toFloat(),
                    onValueChange = { onAction(SettingsUiAction.SetAqiThreshold(it.toInt())) },
                    valueRange = 50f..200f,
                    steps = 14,
                    colors = SliderDefaults.colors(
                        thumbColor = EcoEmeraldBright,
                        activeTrackColor = EcoEmerald,
                        inactiveTrackColor = SurfaceContainerHigh
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Notification and HEPA filtration engage when indoor index exceeds threshold.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = OnSurfaceVariantSlate
                    )
                )
            }
        }
    }
}

/**
 * Interface & Aesthetics Card.
 */
@Composable
private fun InterfaceCard(
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column {
            SettingSwitchRow(
                icon = Icons.Rounded.Security,
                title = "Ultra Frosted Glassmorphism",
                subtitle = "Multi-layer obsidian blur & rim-lighting",
                checked = state.display.ultraGlassShaders,
                accent = QuickActionAccent.CYAN,
                onCheckedChange = { onAction(SettingsUiAction.ToggleUltraGlass(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.Vibration,
                title = "Tactical Haptic Feedback",
                subtitle = "Subtle vibration pulse on switch actuation",
                checked = state.display.tacticalHaptics,
                accent = QuickActionAccent.PURPLE,
                onCheckedChange = { onAction(SettingsUiAction.ToggleTacticalHaptics(it)) }
            )

            SettingsDivider()

            SettingSwitchRow(
                icon = Icons.Rounded.WbSunny,
                title = "Ambient Neon Flare Glows",
                subtitle = "Atmospheric cyan and violet radial glow layers",
                checked = state.display.ambientBackdropGlows,
                accent = QuickActionAccent.EMERALD,
                onCheckedChange = { onAction(SettingsUiAction.ToggleAmbientBackdrops(it)) }
            )

            SettingsDivider()

            // Neon Aura Accent Theme Selector
            Text(
                text = "Neon Aura Accent Theme",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CyberThemeAccent.entries.forEach { accent ->
                    val isSelected = accent == state.display.selectedAccent
                    val color = Color(accent.hexCode)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) color.copy(alpha = 0.20f)
                                else SurfaceContainerLowest
                            )
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) color else OutlineGlass.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = color),
                                onClick = { onAction(SettingsUiAction.SelectThemeAccent(accent)) }
                            )
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = accent.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) color else OnSurfaceVariantSlate
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Grid Security & Diagnostics Card.
 */
@Composable
private fun SecurityCard(
    state: SettingsUiState,
    onAction: (SettingsUiAction) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column {
            // Emergency Grid Lockdown Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AlertCrimson.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = AlertCrimson,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Emergency Grid Isolation",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (state.isEmergencyGridLocked) AlertCrimson else OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = if (state.isEmergencyGridLocked) "GRID LOCKED: Gas valves and relays isolated"
                            else "Instantly cut smart valves & high-voltage nodes",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = if (state.isEmergencyGridLocked) AlertCrimson.copy(alpha = 0.8f) else OnSurfaceVariantSlate
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(
                            if (state.isEmergencyGridLocked) AlertCrimson
                            else SurfaceContainerHigh
                        )
                        .border(
                            width = 1.dp,
                            color = if (state.isEmergencyGridLocked) AlertCrimson else OutlineGlass.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(999.dp)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = AlertCrimson),
                            onClick = { onAction(SettingsUiAction.TriggerEmergencyLockdown) }
                        )
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.isEmergencyGridLocked) "DISARM" else "ARM LOCK",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = if (state.isEmergencyGridLocked) Color.White else AlertCrimson
                        )
                    )
                }
            }

            SettingsDivider()

            // System Diagnostic Action
            val infiniteTransition = rememberInfiniteTransition(label = "diagnosticSpin")
            val rotationAngle by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "spin"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest)
                    .clickable(
                        enabled = !state.isDiagnosticRunning,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = ElectricCyan),
                        onClick = { onAction(SettingsUiAction.RunSystemDiagnostic) }
                    )
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        tint = ElectricCyan,
                        modifier = Modifier
                            .size(20.dp)
                            .then(
                                if (state.isDiagnosticRunning) Modifier.rotate(rotationAngle)
                                else Modifier
                            )
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (state.isDiagnosticRunning) "Running Diagnostic Scan..." else "Run Habitat Self-Diagnostic",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = "Ping 28 wireless nodes, verify enclave keys & radio RF power",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = OnSurfaceVariantSlate
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

/**
 * System Version Watermark.
 */
@Composable
private fun SettingsWatermark() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "NEXUS HABITAT // QUANTUM OS 4.8.2-Q",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = Color(0x9994A3B8)
            )
        )
        Text(
            text = "Engineered with Jetpack Compose • Cyber Glassmorphic Shell",
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp,
                color = OnSurfaceVariantSlate.copy(alpha = 0.4f)
            )
        )
    }
}

/**
 * Row with an icon, title, subtitle, and an interactive NeonPillSwitch.
 */
@Composable
private fun SettingSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    accent: QuickActionAccent,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        when (accent) {
                            QuickActionAccent.CYAN -> ElectricCyan.copy(alpha = 0.15f)
                            QuickActionAccent.PURPLE -> VioletContainer.copy(alpha = 0.40f)
                            QuickActionAccent.EMERALD -> EcoEmerald.copy(alpha = 0.15f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = when (accent) {
                        QuickActionAccent.CYAN -> ElectricCyan
                        QuickActionAccent.PURPLE -> VioletAurora
                        QuickActionAccent.EMERALD -> EcoEmeraldDim
                    },
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnBackgroundSlate50
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = OnSurfaceVariantSlate
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        NeonPillSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            accent = accent
        )
    }
}

/**
 * Subtle divider between setting rows with 12.dp vertical padding.
 */
@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        color = OutlineGlass.copy(alpha = 0.08f),
        thickness = 1.dp
    )
}
