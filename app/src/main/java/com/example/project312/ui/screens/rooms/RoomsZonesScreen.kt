package com.example.project312.ui.screens.rooms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Bed
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Countertops
import androidx.compose.material.icons.rounded.Deck
import androidx.compose.material.icons.rounded.GasMeter
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Kitchen
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.Nightlight
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.SpatialAudio
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.ViewInAr
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbTwilight
import androidx.compose.material.icons.rounded.Weekend
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.RoomCategory
import com.example.project312.model.RoomFilter
import com.example.project312.model.RoomFilterIcon
import com.example.project312.model.RoomItem
import com.example.project312.model.RoomsUiAction
import com.example.project312.model.RoomsUiState
import com.example.project312.ui.components.GlassContainer
import com.example.project312.ui.theme.EcoEmerald
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.EcoEmeraldDim
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanDark
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainer
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.SurfaceContainerLow
import com.example.project312.ui.theme.SurfaceContainerLowest
import com.example.project312.ui.theme.VioletAurora
import com.example.project312.ui.theme.VioletContainer
import com.example.project312.ui.theme.VioletContainerVibrant
import com.example.project312.ui.theme.VioletFixedDim

/**
 * RoomsZonesScreen: Complete UI for the rooms_zones destination.
 * Matches screen.png and DESIGN.md specifications.
 */
@Composable
fun RoomsZonesScreen(
    state: RoomsUiState,
    onAction: (RoomsUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Decorative Ambient Light Backdrops
        RoomsAmbientBackdrops()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. Header & Configure Button
            item {
                RoomsHeader(
                    totalZonesCount = state.totalZonesCount,
                    onConfigureClick = { onAction(RoomsUiAction.ConfigureHabitats) }
                )
            }

            // 2. Horizontal Scrollable Zone Filter Pills
            item {
                ZoneFilterStrip(
                    selectedFilter = state.selectedFilter,
                    onSelectFilter = { onAction(RoomsUiAction.SelectFilter(it)) }
                )
            }

            // 3. Room Cards List
            items(state.rooms, key = { it.id }) { room ->
                when (room.category) {
                    RoomCategory.LIVING_ROOM -> LivingRoomCard(
                        room = room,
                        onToggle = { onAction(RoomsUiAction.ToggleRoomPower(room.id)) }
                    )
                    RoomCategory.MASTER_SUITE -> MasterSuiteCard(
                        room = room,
                        onToggle = { onAction(RoomsUiAction.ToggleRoomPower(room.id)) }
                    )
                    RoomCategory.HOLODECK_STUDIO -> HolodeckStudioCard(
                        room = room,
                        onToggle = { onAction(RoomsUiAction.ToggleRoomPower(room.id)) }
                    )
                    RoomCategory.CYBER_KITCHEN -> CyberKitchenCard(
                        room = room
                    )
                }
            }

            // 4. Pair New Zone or Node Action Button
            item {
                PairNewZoneButton(
                    onClick = { onAction(RoomsUiAction.PairNewNode) }
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
                        TextButton(onClick = { onAction(RoomsUiAction.ClearFeedback) }) {
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
 * Ambient colored glow accents behind the rooms cockpit.
 */
@Composable
private fun RoomsAmbientBackdrops() {
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
            .offset(x = 180.dp, y = 340.dp)
            .size(280.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        VioletContainerVibrant.copy(alpha = 0.22f),
                        VioletContainerVibrant.copy(alpha = 0.05f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
}

/**
 * Active Habitats Header.
 */
@Composable
private fun RoomsHeader(
    totalZonesCount: Int,
    onConfigureClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Active Habitats",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnBackgroundSlate50
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$totalZonesCount Zones synched • Real-time environmental telemetrics",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariantSlate
                )
            )
        }

        // Configure Button
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(SurfaceContainerHigh.copy(alpha = 0.85f))
                .border(1.dp, OutlineGlass.copy(alpha = 0.10f), RoundedCornerShape(999.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = ElectricCyan),
                    onClick = onConfigureClick
                )
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Tune,
                contentDescription = null,
                tint = ElectricCyanFixedDim,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "CONFIGURE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = ElectricCyanFixedDim
                )
            )
        }
    }
}

/**
 * Horizontal Scrollable Zone Filter Pills.
 */
@Composable
private fun ZoneFilterStrip(
    selectedFilter: RoomFilter,
    onSelectFilter: (RoomFilter) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(RoomFilter.entries) { filter ->
            val isSelected = filter == selectedFilter

            val icon: ImageVector? = when (filter.iconType) {
                RoomFilterIcon.DOT -> null
                RoomFilterIcon.WEEKEND -> Icons.Rounded.Weekend
                RoomFilterIcon.COUNTERTOPS -> Icons.Rounded.Countertops
                RoomFilterIcon.BED -> Icons.Rounded.Bed
                RoomFilterIcon.VIEW_IN_AR -> Icons.Rounded.ViewInAr
                RoomFilterIcon.DECK -> Icons.Rounded.Deck
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(
                        if (isSelected) ElectricCyan.copy(alpha = 0.22f)
                        else SurfaceContainerHigh.copy(alpha = 0.65f)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) ElectricCyan.copy(alpha = 0.60f)
                        else OutlineGlass.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(999.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = ElectricCyan),
                        onClick = { onSelectFilter(filter) }
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (filter.iconType == RoomFilterIcon.DOT) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ElectricCyan else OnSurfaceVariantSlate)
                        )
                    } else if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = filter.label,
                            tint = if (isSelected) ElectricCyan else OnSurfaceVariantSlate,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ElectricCyan else OnSurfaceVariantSlate
                        )
                    )
                }
            }
        }
    }
}

/**
 * Room Card 1: Living Room (Hero Card with visual backdrop scrim).
 */
@Composable
private fun LivingRoomCard(
    room: RoomItem,
    onToggle: () -> Unit
) {
    val cardAlpha by animateFloatAsState(
        targetValue = if (room.isPoweredOn) 1.0f else 0.55f,
        label = "cardAlpha"
    )

    GlassContainer(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        // Ambient Neon Room Scrim Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ElectricCyan.copy(alpha = 0.08f),
                            Color.Transparent,
                            SurfaceContainer.copy(alpha = 0.40f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row: Weekend icon + Title + Master Power Toggle
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(ElectricCyan.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Weekend,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = room.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(ElectricCyan)
                            )
                            Text(
                                text = room.subtitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSurfaceVariantSlate
                                )
                            )
                        }
                    }
                }

                // Tactile Master Power Toggle
                MasterPowerToggle(
                    checked = room.isPoweredOn,
                    onToggle = onToggle,
                    accentGradient = Brush.horizontalGradient(listOf(ElectricCyan, ElectricCyanFixedDim))
                )
            }

            // Live Telemetry Snapshot (3 Columns: Climate Optima, Clean Air, Diffused)
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
                    // Climate
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Icon(Icons.Rounded.Thermostat, null, tint = ElectricCyanFixedDim, modifier = Modifier.size(15.dp))
                            Text(room.tempC, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricCyanFixedDim))
                        }
                        Text("Climate Optima", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = OnSurfaceVariantSlate))
                    }

                    // AQI
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Icon(Icons.Rounded.Air, null, tint = EcoEmeraldDim, modifier = Modifier.size(15.dp))
                            Text(room.aqi, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EcoEmeraldDim))
                        }
                        Text("Clean Air", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = OnSurfaceVariantSlate))
                    }

                    // Lux
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Icon(Icons.Rounded.LightMode, null, tint = VioletAurora, modifier = Modifier.size(15.dp))
                            Text(room.lux, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = VioletAurora))
                        }
                        Text("Diffused", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = OnSurfaceVariantSlate))
                    }
                }
            }

            // Ambient Illumination Control Slider Preview
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Rounded.WbTwilight, null, tint = ElectricCyan, modifier = Modifier.size(14.dp))
                        Text("Aurora Gradient Luster", style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariantSlate))
                    }
                    Text("${(room.sliderLevel * 100).toInt()}%", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = ElectricCyan))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.70f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(room.sliderLevel)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(999.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(ElectricCyan, EcoEmeraldBright, VioletAurora)
                                )
                            )
                    )
                }
            }
        }
    }
}

/**
 * Room Card 2: Master Cyber Suite (Deep Indigo & Sleep Isolation).
 */
@Composable
private fun MasterSuiteCard(
    room: RoomItem,
    onToggle: () -> Unit
) {
    val cardAlpha by animateFloatAsState(
        targetValue = if (room.isPoweredOn) 1.0f else 0.55f,
        label = "suiteAlpha"
    )

    GlassContainer(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        // Ambient Violet Flare
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-20).dp)
                .size(160.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            VioletContainerVibrant.copy(alpha = 0.22f),
                            VioletContainerVibrant.copy(alpha = 0.05f),
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
            // Header Row: Bedtime icon + Title + Sleep Toggle
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(VioletContainer.copy(alpha = 0.40f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Bedtime,
                            contentDescription = null,
                            tint = VioletAurora,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = room.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(VioletFixedDim)
                            )
                            Text(
                                text = room.subtitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSurfaceVariantSlate
                                )
                            )
                        }
                    }
                }

                // Sleep Toggle
                MasterPowerToggle(
                    checked = room.isPoweredOn,
                    onToggle = onToggle,
                    accentGradient = Brush.horizontalGradient(listOf(VioletContainerVibrant, VioletAurora)),
                    iconVector = Icons.Rounded.Nightlight
                )
            }

            // 2-Column Telemetry Badges (Sound Shield & Deep Rem Temp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Sound Shield
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh.copy(alpha = 0.40f))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(VioletContainer.copy(alpha = 0.40f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.GraphicEq, null, tint = VioletAurora, modifier = Modifier.size(16.dp))
                    }
                    Column {
                        Text("Sound Shield", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = OnSurfaceVariantSlate))
                        Text(room.noiseDb, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = OnBackgroundSlate50))
                    }
                }

                // Deep Rem Temp
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh.copy(alpha = 0.40f))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(ElectricCyan.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Rounded.AcUnit, null, tint = ElectricCyanFixedDim, modifier = Modifier.size(16.dp))
                    }
                    Column {
                        Text("Deep Rem Temp", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = OnSurfaceVariantSlate))
                        Text(room.deepRemTemp, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = OnBackgroundSlate50))
                    }
                }
            }

            // Sub-bar status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Rounded.Sensors, null, tint = EcoEmeraldDim, modifier = Modifier.size(15.dp))
                    Text("Circadian rhythm cycle: Deep Sleep Mode", style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariantSlate))
                }
                Text("3 items synched", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, color = VioletAurora))
            }
        }
    }
}

/**
 * Room Card 3: Holodeck Studio (Neon Violet & Hyper-Performance).
 */
@Composable
private fun HolodeckStudioCard(
    room: RoomItem,
    onToggle: () -> Unit
) {
    val cardAlpha by animateFloatAsState(
        targetValue = if (room.isPoweredOn) 1.0f else 0.55f,
        label = "studioAlpha"
    )

    GlassContainer(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row: ViewInAr icon + Title + OVERCLOCK tag + Toggle
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(VioletAurora.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ViewInAr,
                            contentDescription = null,
                            tint = VioletAurora,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = room.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnBackgroundSlate50
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(VioletContainer.copy(alpha = 0.50f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "OVERCLOCK",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        color = VioletFixedDim
                                    )
                                )
                            }
                        }
                        Text(
                            text = room.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                    }
                }

                // Overclock Power Toggle
                MasterPowerToggle(
                    checked = room.isPoweredOn,
                    onToggle = onToggle,
                    accentGradient = Brush.horizontalGradient(listOf(VioletContainerVibrant, VioletAurora))
                )
            }

            // Acoustic & Lighting Matrix Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.60f))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Rounded.SpatialAudio, null, tint = ElectricCyan, modifier = Modifier.size(16.dp))
                            Text("Spatial Audio Cluster", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold, color = OnBackgroundSlate50))
                        }
                        Text("${(room.spatialAudioLevel * 100).toInt()}%", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = ElectricCyan))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(7.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(SurfaceContainerHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(room.spatialAudioLevel)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(999.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(VioletContainerVibrant, ElectricCyan)
                                    )
                                )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Dynamic RGB Sync: Reactive Prism", style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariantSlate))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                            Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(EcoEmeraldDim))
                            Text("12 ms latency", style = MaterialTheme.typography.labelSmall.copy(color = EcoEmeraldDim, fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
            }

            // Room Visual Snapshot Strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                VioletContainer.copy(alpha = 0.70f),
                                SurfaceContainerHigh.copy(alpha = 0.60f)
                            )
                        )
                    )
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Rounded.Memory, null, tint = VioletAurora, modifier = Modifier.size(16.dp))
                    Text(
                        text = "${room.gridLoadKw} Grid Load",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnBackgroundSlate50
                        )
                    )
                }
            }
        }
    }
}

/**
 * Room Card 4: Cyber Kitchen (Safety, Climate & Telemetrics).
 */
@Composable
private fun CyberKitchenCard(
    room: RoomItem
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
            // Header: Kitchen icon + Title + Auto-Guarded badge
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(EcoEmeraldBright.copy(alpha = 0.20f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Kitchen,
                            contentDescription = null,
                            tint = EcoEmeraldDim,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = room.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBackgroundSlate50
                            )
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(EcoEmeraldBright)
                            )
                            Text(
                                text = room.subtitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSurfaceVariantSlate
                                )
                            )
                        }
                    }
                }

                // Auto-Guarded Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(EcoEmeraldBright.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.VerifiedUser,
                        contentDescription = null,
                        tint = EcoEmeraldDim,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Auto-Guarded",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = EcoEmeraldDim
                        )
                    )
                }
            }

            // Appliance Sub-Grid (Smart Vault & Water Pure Osmosis)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Smart Vault
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh.copy(alpha = 0.40f))
                        .padding(10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Smart Vault", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, color = OnSurfaceVariantSlate))
                            Icon(Icons.Rounded.AcUnit, null, tint = ElectricCyan, modifier = Modifier.size(15.dp))
                        }
                        Text("-18°C / 4°C", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = OnBackgroundSlate50))
                        Text("Compressor Eco", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = EcoEmeraldDim))
                    }
                }

                // Filtration
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh.copy(alpha = 0.40f))
                        .padding(10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Filtration", style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, color = OnSurfaceVariantSlate))
                            Icon(Icons.Rounded.WaterDrop, null, tint = ElectricCyan, modifier = Modifier.size(15.dp))
                        }
                        Text("99.4% Pure", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = OnBackgroundSlate50))
                        Text("Cartridge optimal", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = ElectricCyanFixedDim))
                    }
                }
            }

            // Inline Environmental Status
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.50f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(Icons.Rounded.GasMeter, null, tint = EcoEmeraldBright, modifier = Modifier.size(14.dp))
                        Text("VOC & Methane Sensors: Nominal", style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariantSlate))
                    }
                    Text("Smart Oven: Idle", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, color = OnBackgroundSlate50))
                }
            }
        }
    }
}

/**
 * Tactile Master Power Toggle Switch for Room Cards.
 */
@Composable
private fun MasterPowerToggle(
    checked: Boolean,
    onToggle: () -> Unit,
    accentGradient: Brush,
    iconVector: ImageVector = Icons.Rounded.Bolt
) {
    val alignmentBias by animateFloatAsState(
        targetValue = if (checked) 1f else -1f,
        label = "powerBias"
    )

    Box(
        modifier = Modifier
            .width(54.dp)
            .height(30.dp)
            .shadow(
                elevation = if (checked) 8.dp else 0.dp,
                shape = RoundedCornerShape(999.dp),
                spotColor = ElectricCyan
            )
            .clip(RoundedCornerShape(999.dp))
            .background(
                if (checked) accentGradient
                else Brush.horizontalGradient(listOf(SurfaceContainerHighest, SurfaceContainerHighest))
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggle
            )
            .padding(horizontal = 4.dp),
        contentAlignment = androidx.compose.ui.BiasAlignment(horizontalBias = alignmentBias, verticalBias = 0f)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .background(SurfaceContainerLowest),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = if (checked) ElectricCyan else OnSurfaceVariantSlate,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}

/**
 * Pair New Zone or Node Glass Action Button.
 */
@Composable
private fun PairNewZoneButton(
    onClick: () -> Unit
) {
    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(ElectricCyan.copy(alpha = 0.20f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = ElectricCyan,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Pair New Zone or Node",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                )
            )
        }
    }
}
