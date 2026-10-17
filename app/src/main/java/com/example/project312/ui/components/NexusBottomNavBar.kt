package com.example.project312.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.MeetingRoom
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.model.NavDestination
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.OutlineGlass
import com.example.project312.ui.theme.SurfaceContainer

@Composable
fun NexusBottomNavBar(
    currentDestination: NavDestination,
    onDestinationSelected: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(SurfaceContainer.copy(alpha = 0.90f))
                .border(
                    width = 1.dp,
                    color = OutlineGlass.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(999.dp)
                )
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavDestination.entries.forEach { destination ->
                val isSelected = destination == currentDestination
                val icon = when (destination) {
                    NavDestination.OVERVIEW -> Icons.Rounded.GridView
                    NavDestination.ROOMS -> Icons.Rounded.MeetingRoom
                    NavDestination.ROUTINES -> Icons.Rounded.Autorenew
                    NavDestination.ENERGY -> Icons.Rounded.EnergySavingsLeaf
                    NavDestination.SETTINGS -> Icons.Rounded.Settings
                }

                NexusNavItem(
                    label = destination.label,
                    icon = icon,
                    isSelected = isSelected,
                    onClick = { onDestinationSelected(destination) }
                )
            }
        }
    }
}

@Composable
private fun NexusNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tintColor by animateColorAsState(
        targetValue = if (isSelected) ElectricCyan else OnSurfaceVariantSlate,
        label = "navItemColor"
    )

    val scaleFactor by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1.0f,
        label = "navItemScale"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = ElectricCyan.copy(alpha = 0.2f)),
                onClick = onClick
            )
            .scale(scaleFactor)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tintColor,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                color = tintColor
            )
        )
    }
}
