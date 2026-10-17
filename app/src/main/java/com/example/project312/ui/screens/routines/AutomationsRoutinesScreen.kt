package com.example.project312.ui.screens.routines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.AltRoute
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Blinds
import androidx.compose.material.icons.rounded.BlindsClosed
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Coffee
import androidx.compose.material.icons.rounded.Contactless
import androidx.compose.material.icons.rounded.DoNotDisturbOn
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.LensBlur
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Radar
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.SmartToy
import androidx.compose.material.icons.rounded.SolarPower
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.VerifiedUser
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material.icons.rounded.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.RoutineActionAccent
import com.example.project312.model.RoutineActionIcon
import com.example.project312.model.RoutineActionItem
import com.example.project312.model.RoutineCategoryIcon
import com.example.project312.model.RoutineTriggerIcon
import com.example.project312.model.RoutinesUiAction
import com.example.project312.model.RoutinesUiState
import com.example.project312.model.ScheduledSequenceItem
import com.example.project312.model.SynapseStats
import com.example.project312.ui.components.GlassContainer
import com.example.project312.ui.components.RoutineSwitch
import com.example.project312.ui.theme.AlertCrimson
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

/**
 * AutomationsRoutinesScreen: Complete UI for the automations_routines destination.
 * Matches screen.png and DESIGN.md specifications.
 */
@Composable
fun AutomationsRoutinesScreen(
    state: RoutinesUiState,
    onAction: (RoutinesUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Decorative Ambient Light Backdrops
        RoutinesAmbientBackdrops()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 100.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. AI Protocol Synapse Banner
            item {
                ProtocolSynapseCard(
                    stats = state.synapseStats
                )
            }

            // 2. Logic Architect (Construct Automation IF/THEN Builder Card)
            item {
                ConstructAutomationCard(
                    onDeploy = { onAction(RoutinesUiAction.DeployNewRoutine) }
                )
            }

            // 3. Scheduled Sequences Header
            item {
                ScheduledSequencesHeader(
                    activeCount = state.routines.count { it.isEnabled },
                    isSortedAlphabetical = state.isSortedAlphabetical,
                    onSortToggle = { onAction(RoutinesUiAction.ToggleSortMatrix) }
                )
            }

            // 4. Scheduled Sequences Routine Cards
            items(state.routines, key = { it.id }) { routine ->
                RoutineSequenceCard(
                    routine = routine,
                    onToggle = { onAction(RoutinesUiAction.ToggleRoutine(routine.id)) }
                )
            }
        }

        // Local feedback snackbar overlay
        AnimatedVisibility(
            visible = state.userFeedback != null,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp, start = 20.dp, end = 20.dp)
        ) {
            state.userFeedback?.let { feedback ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = Color.Black.copy(alpha = 0.50f),
                            spotColor = Color.Black.copy(alpha = 0.50f)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerHighest)
                        .border(1.dp, ElectricCyan.copy(alpha = 0.30f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyan
                        ),
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = ElectricCyan),
                                onClick = { onAction(RoutinesUiAction.ClearFeedback) }
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Ambient colored glow accents behind the routines cockpit.
 */
@Composable
private fun BoxScope.RoutinesAmbientBackdrops() {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(x = 40.dp, y = (-20).dp)
            .size(260.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        ElectricCyan.copy(alpha = 0.18f),
                        ElectricCyan.copy(alpha = 0.04f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .offset(x = (-40).dp, y = 300.dp)
            .size(280.dp)
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
 * 1. AI Protocol Synapse Banner Card.
 */
@Composable
private fun ProtocolSynapseCard(
    stats: SynapseStats
) {
    val transition = rememberInfiniteTransition(label = "synapseDotPulse")
    val dotAlpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row: Brain emblem + Titles + Neural Sync pill
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
                    // Circular Emblem with pulsing dot
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh.copy(alpha = 0.70f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Psychology,
                            contentDescription = null,
                            tint = ElectricCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 2.dp, end = 2.dp)
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(EcoEmeraldBright.copy(alpha = dotAlpha))
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PROTOCOL SYNAPSE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = ElectricCyanFixedDim
                            )
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "AI Autonomous Engine Active",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Neural Sync Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerHighest.copy(alpha = 0.60f))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
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
                        text = "Neural Sync",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EcoEmeraldBright
                        )
                    )
                }
            }

            // 2-Column Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Stat 1: Triggers Fired
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.50f))
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.SmartToy,
                                contentDescription = null,
                                tint = ElectricCyan,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "Triggers Fired",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariantSlate
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${stats.triggersFiredCount}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnBackgroundSlate50
                                )
                            )
                            Text(
                                text = stats.triggersSubtitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = EcoEmeraldBright,
                                    fontWeight = FontWeight.Medium
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 3.dp)
                            )
                        }
                    }
                }

                // Stat 2: Success Vector
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerLowest.copy(alpha = 0.50f))
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.VerifiedUser,
                                contentDescription = null,
                                tint = VioletAurora,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = "Success Vector",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = OnSurfaceVariantSlate
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${stats.successVectorPercent}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnBackgroundSlate50
                                )
                            )
                            Text(
                                text = stats.successSubtitle,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = ElectricCyanFixedDim,
                                    fontWeight = FontWeight.Medium
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2. Logic Architect Card ("Construct Automation" IF/THEN Builder).
 */
@Composable
private fun ConstructAutomationCard(
    onDeploy: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "arrowPulse")
    val arrowAlpha by transition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arrowAlpha"
    )

    GlassContainer(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 24.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Logic Architect + alt_route icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "LOGIC ARCHITECT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = ElectricCyanFixedDim
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Construct Automation",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnBackgroundSlate50
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ElectricCyan.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.AltRoute,
                        contentDescription = null,
                        tint = ElectricCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Horizontal Flow Diagram Box (IF Condition -> THEN Protocol)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLowest.copy(alpha = 0.60f))
                    .border(1.dp, OutlineGlass.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // IF Block
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHighest.copy(alpha = 0.80f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Sensors,
                                contentDescription = null,
                                tint = ElectricCyanFixedDim,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "IF Condition",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = OnSurfaceVariantSlate
                                )
                            )
                            Text(
                                text = "Sensor / Event",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnBackgroundSlate50
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Pulsing Arrow
                    Box(modifier = Modifier.padding(horizontal = 6.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = ElectricCyan.copy(alpha = arrowAlpha),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // THEN Block
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "THEN Protocol",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = OnSurfaceVariantSlate
                                )
                            )
                            Text(
                                text = "Multi-Action",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OnBackgroundSlate50
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(VioletContainer.copy(alpha = 0.40f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Bolt,
                                contentDescription = null,
                                tint = VioletAurora,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

            // Full-Width Pill Button: Deploy New Routine
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(ElectricCyan, Color(0xFF00A3FF))
                        )
                    )
                    .clickable(onClick = onDeploy),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircle,
                        contentDescription = null,
                        tint = ElectricCyanDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Deploy New Routine",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ElectricCyanDark
                        )
                    )
                }
            }
        }
    }
}

/**
 * Scheduled Sequences Section Header.
 */
@Composable
private fun ScheduledSequencesHeader(
    activeCount: Int,
    isSortedAlphabetical: Boolean,
    onSortToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Scheduled Sequences",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = OnBackgroundSlate50
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "$activeCount Active",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 11.sp,
                        color = ElectricCyanFixedDim,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = ElectricCyan),
                    onClick = onSortToggle
                )
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = if (isSortedAlphabetical) "Sorted: A-Z" else "Sort Matrix",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    color = OnSurfaceVariantSlate
                )
            )
            Icon(
                imageVector = Icons.Rounded.Tune,
                contentDescription = null,
                tint = OnSurfaceVariantSlate,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

/**
 * Routine Card with Custom Switch and 2x2 Sub-Action Tiles.
 */
@Composable
private fun RoutineSequenceCard(
    routine: ScheduledSequenceItem,
    onToggle: () -> Unit
) {
    val cardAlpha by animateFloatAsState(
        targetValue = if (routine.isEnabled) 1.0f else 0.55f,
        label = "cardAlpha"
    )

    val categoryIconVector: ImageVector = when (routine.categoryIcon) {
        RoutineCategoryIcon.SUNRISE -> Icons.Rounded.WbTwilight
        RoutineCategoryIcon.MATRIX -> Icons.Rounded.Hub
        RoutineCategoryIcon.SUNSET -> Icons.Rounded.WbTwilight
        RoutineCategoryIcon.SECURITY -> Icons.Rounded.Security
    }

    val categoryTint: Color = when (routine.categoryAccent) {
        RoutineActionAccent.CYAN -> ElectricCyan
        RoutineActionAccent.PURPLE -> VioletAurora
        RoutineActionAccent.EMERALD -> EcoEmeraldBright
        RoutineActionAccent.CRIMSON -> AlertCrimson
        RoutineActionAccent.SLATE -> OnSurfaceVariantSlate
    }

    val triggerIconVector: ImageVector = when (routine.triggerIcon) {
        RoutineTriggerIcon.ALARM -> Icons.Rounded.Alarm
        RoutineTriggerIcon.NFC -> Icons.Rounded.Contactless
        RoutineTriggerIcon.TWILIGHT -> Icons.Rounded.SolarPower
        RoutineTriggerIcon.BEDTIME -> Icons.Rounded.Bedtime
    }

    GlassContainer(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha),
        cornerRadius = 20.dp,
        paddingValues = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Top Row: Category icon, Title, Trigger info, RoutineSwitch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = categoryIconVector,
                            contentDescription = routine.title,
                            tint = categoryTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = routine.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = OnBackgroundSlate50
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = triggerIconVector,
                                contentDescription = null,
                                tint = ElectricCyanFixedDim,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = routine.triggerText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    color = ElectricCyanFixedDim
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Toggle Switch
                RoutineSwitch(
                    checked = routine.isEnabled,
                    onCheckedChange = { onToggle() }
                )
            }

            // 2x2 Sub-Action Tiles
            val row1 = routine.actions.take(2)
            val row2 = routine.actions.drop(2).take(2)

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row1.forEach { action ->
                        ActionSubTile(action = action, modifier = Modifier.weight(1f))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    row2.forEach { action ->
                        ActionSubTile(action = action, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionSubTile(
    action: RoutineActionItem,
    modifier: Modifier = Modifier
) {
    val iconVector: ImageVector = when (action.iconType) {
        RoutineActionIcon.LIGHT_GRADUAL -> Icons.Rounded.LightMode
        RoutineActionIcon.BLINDS -> Icons.Rounded.Blinds
        RoutineActionIcon.BROADCAST -> Icons.Rounded.Podcasts
        RoutineActionIcon.COFFEE -> Icons.Rounded.Coffee
        RoutineActionIcon.BLUE_LIGHT -> Icons.Rounded.LensBlur
        RoutineActionIcon.ANC -> Icons.Rounded.Headphones
        RoutineActionIcon.DND -> Icons.Rounded.DoNotDisturbOn
        RoutineActionIcon.AIRFLOW -> Icons.Rounded.Air
        RoutineActionIcon.AMBER_LIGHT -> Icons.Rounded.Lightbulb
        RoutineActionIcon.PERIMETER -> Icons.Rounded.Shield
        RoutineActionIcon.THERMOSTAT -> Icons.Rounded.Thermostat
        RoutineActionIcon.SHADES -> Icons.Rounded.BlindsClosed
        RoutineActionIcon.LOCK -> Icons.Rounded.Lock
        RoutineActionIcon.RADAR -> Icons.Rounded.Radar
        RoutineActionIcon.CAMERA -> Icons.Rounded.Videocam
        RoutineActionIcon.POWER_CUT -> Icons.Rounded.PowerSettingsNew
    }

    val iconTint: Color = when (action.accent) {
        RoutineActionAccent.CYAN -> ElectricCyan
        RoutineActionAccent.PURPLE -> VioletAurora
        RoutineActionAccent.EMERALD -> EcoEmeraldDim
        RoutineActionAccent.CRIMSON -> AlertCrimson
        RoutineActionAccent.SLATE -> OnSurfaceVariantSlate
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainerLow.copy(alpha = 0.60f))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = action.label,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = OnSurfaceVariantSlate
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
