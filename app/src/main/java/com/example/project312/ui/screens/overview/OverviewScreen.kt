package com.example.project312.ui.screens.overview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Blinds
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.automirrored.rounded.TrendingDown
import androidx.compose.material.icons.rounded.Hvac
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.Speaker
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.DeviceAccent
import com.example.project312.model.EnergyVectorTelemetry
import com.example.project312.model.HabitatTelemetry
import com.example.project312.model.OverviewUiAction
import com.example.project312.model.OverviewUiState
import com.example.project312.model.QuickActionAccent
import com.example.project312.model.QuickActionIcon
import com.example.project312.model.QuickActionItem
import com.example.project312.model.SmartDeviceItem
import com.example.project312.model.SystemMode
import com.example.project312.ui.components.EnergyVectorGraph
import com.example.project312.ui.components.EqualizerBars
import com.example.project312.ui.components.GlassContainer
import com.example.project312.ui.components.NeonPillSwitch
import com.example.project312.ui.theme.EcoEmerald
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.EcoEmeraldDim
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanFixed
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.SurfaceContainerLow
import com.example.project312.ui.theme.SurfaceObsidian
import com.example.project312.ui.theme.VioletAurora
import com.example.project312.ui.theme.VioletContainerVibrant
import com.example.project312.ui.theme.VioletFixedDim

/**
 * OverviewScreen (FIRST SCREEN): Smart Home Overview for Nexus Habitat.
 * Faithful reproduction of screen.png and DESIGN.md.
 * Strictly adheres to UDF with (state, onAction).
 */
@Composable
fun OverviewScreen(
    state: OverviewUiState,
    onAction: (OverviewUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            // 1. Habitat Telemetry Hero Card
            item {
                TelemetryHeroCard(
                    telemetry = state.telemetry
                )
            }

            // 2. System Mode Selector
            item {
                SystemModesSection(
                    currentMode = state.currentMode,
                    onModeSelected = { onAction(OverviewUiAction.ChangeMode(it)) }
                )
            }

            // 3. Instant Actions 2x2 Grid
            item {
                InstantActionsSection(
                    actions = state.instantActions,
                    onToggleAction = { onAction(OverviewUiAction.ToggleInstantAction(it)) }
                )
            }

            // 4. Energy Consumption Mini Visualizer
            item {
                EnergyVectorSection(
                    energyVector = state.energyVector
                )
            }

            // 5. Active Devices 2x2 Grid
            item {
                ActiveDevicesSection(
                    devices = state.activeDevices,
                    onDeviceToggle = { onAction(OverviewUiAction.ToggleDevicePower(it)) },
                    onDeviceLevelChange = { id, lvl -> onAction(OverviewUiAction.SetDeviceLevel(id, lvl)) }
                )
            }
        }

        // Floating In-Place Feedback Banner matching Flutter
        AnimatedVisibility(
            visible = state.feedbackMessage != null,
            enter = fadeIn(animationSpec = tween(200)) + slideInVertically(animationSpec = tween(250)) { it / 2 },
            exit = fadeOut(animationSpec = tween(150)) + slideOutVertically(animationSpec = tween(200)) { it / 2 },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            state.feedbackMessage?.let { message ->
                FeedbackToast(
                    message = message,
                    onDismiss = { onAction(OverviewUiAction.ClearFeedback) }
                )
            }
        }
    }
}


/**
 * Hero Telemetry Card for Sector Alpha.
 */
@Composable
private fun TelemetryHeroCard(
    telemetry: HabitatTelemetry
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heroPingTransition")
    val pingAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pingAlpha"
    )

    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(18.dp)
    ) {
        // Top-right Cyber Cyan Glass Glow Flare matching spec
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .size(160.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ElectricCyan.copy(alpha = 0.22f),
                            ElectricCyan.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Row: Status badge and Sensor count
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f, fill = false)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ElectricCyan.copy(alpha = pingAlpha))
                        )
                        Text(
                            text = "HABITAT TELEMETRY ACTIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = ElectricCyanFixedDim
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = telemetry.sectorName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnBackgroundSlate50
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Sensor count pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerHighest.copy(alpha = 0.70f))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Sensors,
                        contentDescription = "Active Sensors",
                        tint = EcoEmeraldDim,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = "${telemetry.activeSensorsCount} Active",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                }
            }

            // 3-Column Metric Readouts Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Metric 1: Temperature
                MetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Rounded.Thermostat,
                    iconTint = ElectricCyan,
                    iconBg = ElectricCyan.copy(alpha = 0.16f),
                    value = "${telemetry.ambientTemp}°",
                    label = "Ambient"
                )

                // Metric 2: Humidity
                MetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Rounded.WaterDrop,
                    iconTint = ElectricCyanFixed,
                    iconBg = ElectricCyanFixed.copy(alpha = 0.16f),
                    value = "${telemetry.humidityPercent}%",
                    label = "Humidity"
                )

                // Metric 3: Air Quality Index (AQI)
                MetricTile(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Rounded.Air,
                    iconTint = EcoEmeraldBright,
                    iconBg = EcoEmeraldBright.copy(alpha = 0.18f),
                    value = "AQI ${telemetry.aqi}",
                    valueColor = EcoEmeraldBright,
                    label = telemetry.aqiStatus
                )
            }

            // Sub-bar status pill
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Verified,
                        contentDescription = "Verified status",
                        tint = EcoEmeraldDim,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Biometric air scrubbers running",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariantSlate
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "${telemetry.airPurityPercent}% Purity",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ElectricCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
private fun MetricTile(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color = OnBackgroundSlate50
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerLow.copy(alpha = 0.70f))
            .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = valueColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    color = OnSurfaceVariantSlate
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * System Modes Horizontal Scrollable Chips.
 */
@Composable
private fun SystemModesSection(
    currentMode: SystemMode,
    onModeSelected: (SystemMode) -> Unit
) {
    val modes = listOf(
        SystemMode.HOME,
        SystemMode.AWAY,
        SystemMode.NIGHT_PROTOCOL,
        SystemMode.CINEMA_MODE,
        SystemMode.PARTY_AURA
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "System Modes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Dynamic Shift",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariantSlate
                )
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(modes) { mode ->
                val isActive = mode == currentMode
                val modeIcon: ImageVector = when (mode) {
                    SystemMode.HOME -> Icons.Rounded.Thermostat
                    SystemMode.AWAY -> Icons.AutoMirrored.Rounded.DirectionsWalk
                    SystemMode.NIGHT_PROTOCOL -> Icons.Rounded.Bedtime
                    SystemMode.CINEMA_MODE -> Icons.Rounded.Movie
                    SystemMode.PARTY_AURA -> Icons.Rounded.AutoAwesome
                }

                val chipBgColor by animateColorAsState(
                    targetValue = if (isActive) ElectricCyan.copy(alpha = 0.22f)
                    else SurfaceContainerHigh.copy(alpha = 0.65f),
                    animationSpec = tween(durationMillis = 200),
                    label = "chipBgColor"
                )
                val chipBorderColor by animateColorAsState(
                    targetValue = if (isActive) ElectricCyan.copy(alpha = 0.60f)
                    else OutlineGlass.copy(alpha = 0.10f),
                    animationSpec = tween(durationMillis = 200),
                    label = "chipBorderColor"
                )
                val chipTextColor by animateColorAsState(
                    targetValue = if (isActive) ElectricCyan else OnSurfaceVariantSlate,
                    animationSpec = tween(durationMillis = 200),
                    label = "chipTextColor"
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(chipBgColor)
                        .border(
                            width = 1.dp,
                            color = chipBorderColor,
                            shape = RoundedCornerShape(999.dp)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(color = ElectricCyan),
                            onClick = { onModeSelected(mode) }
                        )
                        .padding(horizontal = 16.dp, vertical = 9.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (isActive) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCyan)
                            )
                        } else {
                            Icon(
                                imageVector = modeIcon,
                                contentDescription = mode.label,
                                tint = OnSurfaceVariantSlate,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Text(
                            text = mode.label,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                color = chipTextColor
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Instant Actions 2x2 Glassmorphic Control Cards.
 */
@Composable
private fun InstantActionsSection(
    actions: List<QuickActionItem>,
    onToggleAction: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Instant Actions",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "One-Tap Execute",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = ElectricCyanFixedDim,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        // 2x2 Grid using Columns and Rows
        val firstRow = actions.take(2)
        val secondRow = actions.drop(2).take(2)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            firstRow.forEach { action ->
                InstantActionCard(
                    modifier = Modifier.weight(1f),
                    item = action,
                    onToggle = { onToggleAction(action.id) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            secondRow.forEach { action ->
                InstantActionCard(
                    modifier = Modifier.weight(1f),
                    item = action,
                    onToggle = { onToggleAction(action.id) }
                )
            }
        }
    }
}

@Composable
private fun InstantActionCard(
    item: QuickActionItem,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconVector: ImageVector = when (item.icon) {
        QuickActionIcon.LIGHT -> Icons.Rounded.LightMode
        QuickActionIcon.CLIMATE -> Icons.Rounded.Hvac
        QuickActionIcon.LOCK -> Icons.Rounded.Lock
        QuickActionIcon.FAN -> Icons.Rounded.Air
    }

    val iconTint: Color = when (item.accent) {
        QuickActionAccent.CYAN -> ElectricCyan
        QuickActionAccent.PURPLE -> VioletAurora
        QuickActionAccent.EMERALD -> EcoEmeraldBright
    }

    val iconBg: Color = when (item.accent) {
        QuickActionAccent.CYAN -> ElectricCyan.copy(alpha = 0.18f)
        QuickActionAccent.PURPLE -> VioletContainerVibrant.copy(alpha = 0.28f)
        QuickActionAccent.EMERALD -> EcoEmeraldBright.copy(alpha = 0.20f)
    }

    val subtitleColor: Color = when (item.accent) {
        QuickActionAccent.CYAN -> ElectricCyan
        QuickActionAccent.PURPLE -> VioletAurora
        QuickActionAccent.EMERALD -> if (item.isEnabled) EcoEmeraldBright else OnSurfaceVariantSlate
    }

    GlassContainer(
        modifier = modifier,
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(14.dp),
        onClick = onToggle
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Top Row: Icon circle + Neon Pill Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = item.title,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                NeonPillSwitch(
                    checked = item.isEnabled,
                    accent = item.accent,
                    onCheckedChange = { onToggle() }
                )
            }

            // Bottom Column: Title + Subtitle
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnBackgroundSlate50
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = subtitleColor,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Energy Vector Telemetry Card with custom Bézier flow graph.
 */
@Composable
private fun EnergyVectorSection(
    energyVector: EnergyVectorTelemetry
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header Row: Bolt badge + titles + right consumption readout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(EcoEmeraldBright.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Bolt,
                            contentDescription = "Energy Vector",
                            tint = EcoEmeraldBright,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Energy Vector",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Real-time Telemetry",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "${energyVector.currentKwh}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ElectricCyan
                            )
                        )
                        Text(
                            text = "kWh",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = OnBackgroundSlate50,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.TrendingDown,
                            contentDescription = "Trending Down",
                            tint = EcoEmeraldDim,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "${energyVector.deltaPercent}% vs yesterday",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = EcoEmeraldDim,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Custom Canvas Spline Curve
            EnergyVectorGraph(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Timeline labels: 06:00, 12:00, 18:00, Now
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                energyVector.timeLabels.forEach { label ->
                    val isNow = label == "Now"
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isNow) FontWeight.Bold else FontWeight.Normal,
                            color = if (isNow) EcoEmeraldBright else OnSurfaceVariantSlate
                        )
                    )
                }
            }
        }
    }
}

/**
 * Active Devices 2x2 Grid with real-time indicators and sliders.
 */
@Composable
private fun ActiveDevicesSection(
    devices: List<SmartDeviceItem>,
    onDeviceToggle: (String) -> Unit,
    onDeviceLevelChange: (String, Float) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Active Devices",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "4 online in zone",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurfaceVariantSlate
                )
            )
        }

        // 2x2 Device Grid
        val firstRow = devices.take(2)
        val secondRow = devices.drop(2).take(2)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            firstRow.forEach { device ->
                DeviceCard(
                    modifier = Modifier.weight(1f),
                    device = device,
                    onToggle = { onDeviceToggle(device.id) },
                    onLevelChange = { onDeviceLevelChange(device.id, it) }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            secondRow.forEach { device ->
                DeviceCard(
                    modifier = Modifier.weight(1f),
                    device = device,
                    onToggle = { onDeviceToggle(device.id) },
                    onLevelChange = { onDeviceLevelChange(device.id, it) }
                )
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: SmartDeviceItem,
    onToggle: () -> Unit,
    onLevelChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconVector: ImageVector = when (device.id) {
        "cyberlight_pro" -> Icons.Rounded.WbTwilight
        "climate_pod" -> Icons.Rounded.AcUnit
        "soundwave_ultra" -> Icons.Rounded.Speaker
        else -> Icons.Rounded.Blinds
    }

    val iconTint: Color = when (device.accent) {
        DeviceAccent.CYAN -> ElectricCyan
        DeviceAccent.CYAN_PURPLE_GRADIENT -> ElectricCyanFixedDim
        DeviceAccent.PURPLE -> VioletAurora
        DeviceAccent.EMERALD -> EcoEmeraldBright
    }

    val iconBg: Color = when (device.accent) {
        DeviceAccent.CYAN -> ElectricCyan.copy(alpha = 0.18f)
        DeviceAccent.CYAN_PURPLE_GRADIENT -> ElectricCyanFixedDim.copy(alpha = 0.18f)
        DeviceAccent.PURPLE -> VioletContainerVibrant.copy(alpha = 0.28f)
        DeviceAccent.EMERALD -> EcoEmeraldBright.copy(alpha = 0.20f)
    }

    val badgeBg: Color = when (device.accent) {
        DeviceAccent.CYAN -> ElectricCyan.copy(alpha = 0.15f)
        DeviceAccent.CYAN_PURPLE_GRADIENT -> ElectricCyanFixedDim.copy(alpha = 0.15f)
        DeviceAccent.PURPLE -> VioletContainerVibrant.copy(alpha = 0.20f)
        DeviceAccent.EMERALD -> EcoEmeraldBright.copy(alpha = 0.15f)
    }

    val badgeTextColor: Color = when (device.accent) {
        DeviceAccent.CYAN -> ElectricCyan
        DeviceAccent.CYAN_PURPLE_GRADIENT -> ElectricCyanFixedDim
        DeviceAccent.PURPLE -> VioletAurora
        DeviceAccent.EMERALD -> EcoEmeraldBright
    }

    GlassContainer(
        modifier = modifier,
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(14.dp),
        onClick = onToggle
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top Row: Icon circle + Badge / Equalizer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = device.name,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (device.isPlayingAudio) {
                    EqualizerBars(
                        isAnimating = device.isPoweredOn,
                        barColor = VioletAurora,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(badgeBg)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = device.badgeText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = badgeTextColor
                            )
                        )
                    }
                }
            }

            // Title & Subtitle
            Column {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OnBackgroundSlate50
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = device.subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (device.id == "soundwave_ultra") VioletAurora else OnSurfaceVariantSlate
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Glowing Progress Slider Bar
            val fillBrush: Brush = when (device.accent) {
                DeviceAccent.CYAN -> Brush.horizontalGradient(
                    listOf(ElectricCyan, ElectricCyan)
                )
                DeviceAccent.CYAN_PURPLE_GRADIENT -> Brush.horizontalGradient(
                    listOf(ElectricCyan, VioletFixedDim)
                )
                DeviceAccent.PURPLE -> Brush.horizontalGradient(
                    listOf(VioletContainerVibrant, VioletAurora)
                )
                DeviceAccent.EMERALD -> Brush.horizontalGradient(
                    listOf(EcoEmeraldBright, EcoEmerald)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceContainerHighest.copy(alpha = 0.80f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = if (device.isPoweredOn) device.level else 0f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(999.dp))
                        .background(fillBrush)
                )
            }
        }
    }
}

/**
 * Floating in-place feedback toast matching Flutter's _FeedbackToast.
 */
@Composable
private fun FeedbackToast(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(14.dp),
                ambientColor = Color.Black.copy(alpha = 0.40f),
                spotColor = Color.Black.copy(alpha = 0.40f)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceContainerHighest)
            .border(1.dp, OutlineGlass, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = OnBackgroundSlate50
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            TextButton(
                onClick = onDismiss,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = ElectricCyan,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
