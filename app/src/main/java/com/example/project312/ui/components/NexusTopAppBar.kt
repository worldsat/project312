package com.example.project312.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.SurfaceObsidian

@Composable
fun NexusTopAppBar(
    habitatName: String = "Nexus Habitat",
    statusText: String = "Optimal",
    temperatureText: String = "24°C",
    hasUnreadNotification: Boolean = true,
    onNotificationClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "topBarDotPulse")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceObsidian.copy(alpha = 0.88f))
            .border(
                width = 1.dp,
                color = OutlineGlass.copy(alpha = 0.10f)
            )
            .statusBarsPadding()
            .height(68.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Brand icon + Habitat Title Block
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Cyber Brand Emblem
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainerHigh.copy(alpha = 0.70f))
                        .border(1.dp, ElectricCyan.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Nexus Habitat Brand",
                        tint = ElectricCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = habitatName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBackgroundSlate50
                            )
                        )
                        // Live green pulse dot
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(EcoEmeraldBright.copy(alpha = dotAlpha))
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariantSlate
                            )
                        )
                        Icon(
                            imageVector = Icons.Rounded.Thermostat,
                            contentDescription = "Temperature",
                            tint = ElectricCyan,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = temperatureText,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ElectricCyanFixedDim,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            // Right: Actions (Notification bell + User Avatar)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Notification Button with glowing unread dot
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = true, color = ElectricCyan),
                            onClick = onNotificationClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = OnSurfaceVariantSlate,
                        modifier = Modifier.size(22.dp)
                    )
                    if (hasUnreadNotification) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 8.dp, end = 8.dp)
                                .size(7.dp)
                                .shadow(4.dp, CircleShape, spotColor = ElectricCyan)
                                .clip(CircleShape)
                                .background(ElectricCyan)
                        )
                    }
                }

                // Avatar Icon
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0FDFF))
                        .border(1.dp, Color.White.copy(alpha = 0.20f), CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = true, color = ElectricCyan),
                            onClick = onAvatarClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "User Profile",
                        tint = Color(0xFF00373A),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
