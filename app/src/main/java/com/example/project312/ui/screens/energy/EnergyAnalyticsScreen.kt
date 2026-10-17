package com.example.project312.ui.screens.energy

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingDown
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.DesktopWindows
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.SolarPower
import androidx.compose.material.icons.rounded.SyncAlt
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material.icons.rounded.Water
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.ApplianceAccent
import com.example.project312.model.ApplianceDrainItem
import com.example.project312.model.ConsumptionDynamicsData
import com.example.project312.model.EnergyUiAction
import com.example.project312.model.EnergyUiState
import com.example.project312.model.NexusIntelligenceData
import com.example.project312.model.PowerFluxTelemetry
import com.example.project312.model.TimelinePeriod
import com.example.project312.ui.components.ConsumptionDynamicsGraph
import com.example.project312.ui.components.GlassContainer
import com.example.project312.ui.components.PowerFluxGauge
import com.example.project312.ui.theme.EcoEmerald
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.EcoEmeraldDim
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanDark
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.SurfaceContainerLow
import com.example.project312.ui.theme.SurfaceContainerLowest
import com.example.project312.ui.theme.VioletAurora
import com.example.project312.ui.theme.VioletContainer
import com.example.project312.ui.theme.VioletContainerVibrant

/**
 * EnergyAnalyticsScreen: Complete UI for the energy_analytics destination.
 * Matches screen.png and DESIGN.md specifications.
 */
@Composable
fun EnergyAnalyticsScreen(
    state: EnergyUiState,
    onAction: (EnergyUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Decorative Ambient Light Backdrops
        EnergyAmbientBackdrops()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Section Header & Micro Status
            item {
                EnergyMatrixHeader()
            }

            // 2. Real-Time Energy Nexus Card (Power Flux)
            item {
                PowerFluxSection(
                    powerFlux = state.powerFlux,
                    isOptimized = state.intelligence.isOptimizationApplied
                )
            }

            // 3. Consumption Dynamics & Segmented Tabs
            item {
                ConsumptionDynamicsSection(
                    data = state.consumptionDynamics,
                    onPeriodSelect = { onAction(EnergyUiAction.SelectPeriod(it)) }
                )
            }

            // 4. Top Consuming Devices Breakdown
            item {
                ActiveApplianceDrainSection(
                    appliances = state.appliances,
                    onApplyEcoTip = { onAction(EnergyUiAction.ApplyApplianceEcoTip(it)) }
                )
            }

            // 5. Eco-Score & AI Recommendations (Nexus Intelligence)
            item {
                NexusIntelligenceSection(
                    intelligence = state.intelligence,
                    onApplyOptimization = { onAction(EnergyUiAction.ApplyAutoPowerOptimization) }
                )
            }
        }

        // Local feedback snackbar
        state.userFeedback?.let { feedback ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
            ) {
                Snackbar(
                    action = {
                        TextButton(onClick = { onAction(EnergyUiAction.ClearFeedback) }) {
                            Text("OK", color = ElectricCyan)
                        }
                    },
                    containerColor = SurfaceContainerHighest,
                    contentColor = OnBackgroundSlate50
                ) {
                    Text(text = feedback, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

/**
 * Ambient Light Backdrops for authentic cyber glassmorphism depth.
 */
@Composable
private fun EnergyAmbientBackdrops() {
    Box(
        modifier = Modifier
            .offset(x = (-40).dp, y = (-20).dp)
            .size(260.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ElectricCyan.copy(alpha = 0.20f),
                        ElectricCyan.copy(alpha = 0.05f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
    Box(
        modifier = Modifier
            .offset(x = 180.dp, y = 140.dp)
            .size(280.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        EcoEmeraldBright.copy(alpha = 0.18f),
                        EcoEmeraldBright.copy(alpha = 0.04f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
    Box(
        modifier = Modifier
            .offset(x = 40.dp, y = 520.dp)
            .size(300.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        VioletContainerVibrant.copy(alpha = 0.22f),
                        VioletContainerVibrant.copy(alpha = 0.06f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
}

/**
 * Top title block: Energy Matrix & Grid Sync micro status.
 */
@Composable
private fun EnergyMatrixHeader() {
    val transition = rememberInfiniteTransition(label = "pulsePulse")
    val dotAlpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Energy Matrix",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundSlate50
                    )
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(EcoEmeraldDim.copy(alpha = dotAlpha))
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Live telemetry & predictive eco-routing",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariantSlate
                )
            )
        }

        // Grid Sync Pill
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(SurfaceContainerHigh.copy(alpha = 0.85f))
                .border(1.dp, OutlineGlass.copy(alpha = 0.10f), RoundedCornerShape(999.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Verified,
                contentDescription = null,
                tint = EcoEmeraldDim,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = "Grid Sync",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = EcoEmeraldBright
                )
            )
        }
    }
}

/**
 * Real-Time Energy Nexus Card (Power Flux Gauge & Solar vs Grid sub-panels).
 */
@Composable
private fun PowerFluxSection(
    powerFlux: PowerFluxTelemetry,
    isOptimized: Boolean
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row: Power Flux + Update status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.SyncAlt,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "Power Flux",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.60f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = powerFlux.lastUpdatedText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariantSlate
                        )
                    )
                }
            }

            // Radial Glow Concentric Gauge
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                PowerFluxGauge(
                    totalDemandKw = powerFlux.totalDemandKw,
                    selfSustainedPercent = powerFlux.selfSustainedPercent,
                    demandFraction = powerFlux.demandArcFraction,
                    ghostFraction = powerFlux.ghostArcFraction,
                    isOptimized = isOptimized
                )
            }

            // Sub-Panels: Solar Inflow vs Grid Pull
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Solar Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow.copy(alpha = 0.70f))
                        .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(EcoEmeraldBright)
                                )
                                Text(
                                    text = "Solar Inflow",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnSurfaceVariantSlate
                                    )
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.SolarPower,
                                contentDescription = null,
                                tint = EcoEmeraldBright,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "+${powerFlux.solarInflowKw}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EcoEmeraldBright
                                )
                            )
                            Text(
                                text = " kW",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = EcoEmeraldDim,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${powerFlux.activePanelsCount} panels active",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate.copy(alpha = 0.8f)
                            )
                        )
                    }
                }

                // Grid Draw Card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLow.copy(alpha = 0.70f))
                        .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(VioletAurora)
                                )
                                Text(
                                    text = "Grid Pull",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnSurfaceVariantSlate
                                    )
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.ElectricBolt,
                                contentDescription = null,
                                tint = VioletAurora,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${powerFlux.gridPullKw}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = VioletAurora
                                )
                            )
                            Text(
                                text = " kW",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = VioletAurora.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = powerFlux.gridTierRate,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate.copy(alpha = 0.8f)
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Consumption Dynamics with timeline filter pills and wave graph.
 */
@Composable
private fun ConsumptionDynamicsSection(
    data: ConsumptionDynamicsData,
    onPeriodSelect: (TimelinePeriod) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header with Title and Segmented Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Consumption Dynamics",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                    Text(
                        text = "Timeline variance & peak telemetry",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariantSlate
                        )
                    )
                }

                // Filter Pill Group (Day, Week, Month, Year)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.80f))
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    TimelinePeriod.entries.forEach { period ->
                        val isSelected = period == data.selectedPeriod
                        val bg by animateColorAsState(
                            targetValue = if (isSelected) ElectricCyan else Color.Transparent,
                            label = "tabBg"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) ElectricCyanDark else OnSurfaceVariantSlate,
                            label = "tabText"
                        )

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(bg)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { onPeriodSelect(period) }
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = period.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = textColor
                                )
                            )
                        }
                    }
                }
            }

            // Inner Wave Curve Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.60f))
                    .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Off-peak badge row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCyanFixedDim)
                            )
                            Text(
                                text = data.offPeakWindow,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ElectricCyanFixedDim
                                )
                            )
                        }

                        Text(
                            text = data.costDelta,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = EcoEmeraldBright
                            )
                        )
                    }

                    // Vector Bézier Wave Graph
                    ConsumptionDynamicsGraph(
                        peakLabel = data.peakLabel,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Horizontal Time Ticks
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        data.timeLabels.forEach { tick ->
                            Text(
                                text = tick,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = OnSurfaceVariantSlate
                                )
                            )
                        }
                    }
                }
            }

            // Quick Insights Footer Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Daily Run Rate
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow.copy(alpha = 0.50f))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.TrendingDown,
                        contentDescription = null,
                        tint = EcoEmeraldDim,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = "Daily Run Rate",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            )
                        )
                        Text(
                            text = data.dailyRunRate,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                    }
                }

                // Cycle Savings
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLow.copy(alpha = 0.50f))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Savings,
                        contentDescription = null,
                        tint = ElectricCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = "Cycle Savings",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            )
                        )
                        Text(
                            text = data.cycleSavings,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Top Consuming Devices Breakdown with interactive Eco-Tips.
 */
@Composable
private fun ActiveApplianceDrainSection(
    appliances: List<ApplianceDrainItem>,
    onApplyEcoTip: (String) -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Active Appliance Drain",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                    Text(
                        text = "Real-time hardware power allotment",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariantSlate
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${appliances.size} Active",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                }
            }

            // List of Appliance Cards
            appliances.forEach { appliance ->
                ApplianceCardItem(
                    item = appliance,
                    onApplyEcoTip = { onApplyEcoTip(appliance.id) }
                )
            }
        }
    }
}

@Composable
private fun ApplianceCardItem(
    item: ApplianceDrainItem,
    onApplyEcoTip: () -> Unit
) {
    val iconVector: ImageVector = when (item.id) {
        "climate_control" -> Icons.Rounded.Air
        "water_heater" -> Icons.Rounded.Water
        "ambient_lighting" -> Icons.Rounded.Lightbulb
        else -> Icons.Rounded.DesktopWindows
    }

    val iconTint: Color = when (item.accent) {
        ApplianceAccent.CYAN -> ElectricCyan
        ApplianceAccent.PURPLE -> VioletAurora
        ApplianceAccent.CYAN_DIM -> ElectricCyanFixedDim
        ApplianceAccent.EMERALD -> EcoEmeraldDim
    }

    val iconBg: Color = when (item.accent) {
        ApplianceAccent.CYAN -> ElectricCyan.copy(alpha = 0.16f)
        ApplianceAccent.PURPLE -> VioletContainerVibrant.copy(alpha = 0.22f)
        ApplianceAccent.CYAN_DIM -> ElectricCyanFixedDim.copy(alpha = 0.16f)
        ApplianceAccent.EMERALD -> EcoEmeraldBright.copy(alpha = 0.16f)
    }

    val barColor: Color = when (item.accent) {
        ApplianceAccent.CYAN -> ElectricCyan
        ApplianceAccent.PURPLE -> VioletAurora
        ApplianceAccent.CYAN_DIM -> ElectricCyanFixedDim
        ApplianceAccent.EMERALD -> EcoEmeraldDim
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow.copy(alpha = 0.80f))
            .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                            contentDescription = item.name,
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = item.zoneDescription,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "${item.powerKw}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = "kW",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                    Text(
                        text = "${item.percentage}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = barColor
                        )
                    )
                }
            }

            // Glass Progress Slot
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.80f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(item.percentage / 100f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(999.dp))
                        .background(barColor)
                )
            }

            // Optional Inline Eco-Optimization Tip
            if (item.ecoTip != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHighest.copy(alpha = 0.40f))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Eco,
                            contentDescription = null,
                            tint = EcoEmeraldDim,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = item.ecoTip,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = OnSurfaceVariantSlate
                            ),
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(
                                if (item.isEcoTipApplied) EcoEmeraldBright.copy(alpha = 0.5f)
                                else EcoEmeraldBright
                            )
                            .clickable(
                                enabled = !item.isEcoTipApplied,
                                onClick = onApplyEcoTip
                            )
                            .padding(horizontal = 10.dp, vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (item.isEcoTipApplied) "Shifted" else "Apply",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF003824)
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Eco-Score & AI Recommendations (Nexus Intelligence).
 */
@Composable
private fun NexusIntelligenceSection(
    intelligence: NexusIntelligenceData,
    onApplyOptimization: () -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        // Internal Emerald Ambient Bloom
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-20).dp)
                .size(160.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            EcoEmeraldBright.copy(alpha = 0.20f),
                            EcoEmeraldBright.copy(alpha = 0.04f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Brain icon + Nexus Intelligence + AI Active
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(EcoEmeraldBright.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Psychology,
                            contentDescription = null,
                            tint = EcoEmeraldBright,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Nexus Intelligence",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Text(
                            text = "Real-time dynamic mesh optimization",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(EcoEmeraldBright.copy(alpha = 0.20f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "AI Active",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = EcoEmeraldBright
                        )
                    )
                }
            }

            // Eco Score Badge & Visual Meter
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLow.copy(alpha = 0.80f))
                    .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Circular Mini Radial Progress (Score 94/100)
                    Box(
                        modifier = Modifier.size(52.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(52.dp)) {
                            val strokeW = 4.dp.toPx()
                            val d = size.minDimension - strokeW
                            val r = d / 2f
                            val c = Offset(size.width / 2f, size.height / 2f)
                            val tl = Offset(c.x - r, c.y - r)
                            val s = Size(d, d)

                            // Base track
                            drawArc(
                                color = SurfaceContainerHigh,
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                topLeft = tl,
                                size = s,
                                style = Stroke(strokeW)
                            )
                            // Active arc
                            drawArc(
                                color = EcoEmeraldBright,
                                startAngle = -90f,
                                sweepAngle = 360f * (intelligence.ecoScore / 100f),
                                useCenter = false,
                                topLeft = tl,
                                size = s,
                                style = Stroke(strokeW, cap = StrokeCap.Round)
                            )
                        }

                        Icon(
                            imageVector = Icons.Rounded.EnergySavingsLeaf,
                            contentDescription = null,
                            tint = EcoEmeraldBright,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${intelligence.ecoScore}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnBackgroundSlate50
                                )
                            )
                            Text(
                                text = " / ${intelligence.maxScore}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurfaceVariantSlate
                                ),
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                        }
                        Text(
                            text = intelligence.habitatStatus,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = EcoEmeraldBright
                            )
                        )
                        Text(
                            text = intelligence.comparisonText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                }
            }

            // AI Suggestion & CTA Action Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerHigh.copy(alpha = 0.45f))
                    .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AutoAwesome,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text(
                                text = intelligence.suggestionTitle,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnBackgroundSlate50
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = intelligence.suggestionDescription,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurfaceVariantSlate
                                )
                            )
                        }
                    }

                    // CTA Button: Linear Gradient Full-Width Pill
                    val isEngaged = intelligence.isOptimizationApplied
                    val ctaBg = if (isEngaged) {
                        Brush.horizontalGradient(listOf(EcoEmeraldBright, EcoEmerald))
                    } else {
                        Brush.horizontalGradient(listOf(ElectricCyan, Color(0xFF00A3FF)))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(ctaBg)
                            .clickable(
                                enabled = !isEngaged,
                                onClick = onApplyOptimization
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Bolt,
                                contentDescription = null,
                                tint = if (isEngaged) Color(0xFF003824) else ElectricCyanDark,
                                modifier = Modifier.size(19.dp)
                            )
                            Text(
                                text = if (isEngaged) {
                                    "Optimization Engaged • 0.4 kWh Preserved"
                                } else {
                                    "Apply Auto-Power Optimization (Save 0.4 kWh)"
                                },
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isEngaged) Color(0xFF003824) else ElectricCyanDark
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
