package com.example.project312.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanDark
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.SurfaceContainerLowest

/**
 * Custom Routine Toggle Switch featuring an embedded icon (check/close) in the knob.
 * Recreates the exact toggle button from the automations_routines design specification.
 */
@Composable
fun RoutineSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val trackColor by animateColorAsState(
        targetValue = if (checked) ElectricCyan else SurfaceContainerHighest,
        label = "routineTrackColor"
    )

    val alignmentBias by animateFloatAsState(
        targetValue = if (checked) 1f else -1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "routineBias"
    )

    Box(
        modifier = modifier
            .width(54.dp)
            .height(30.dp)
            .shadow(
                elevation = if (checked) 8.dp else 0.dp,
                shape = RoundedCornerShape(999.dp),
                spotColor = ElectricCyan
            )
            .clip(RoundedCornerShape(999.dp))
            .background(trackColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCheckedChange(!checked)
            }
            .padding(horizontal = 4.dp),
        contentAlignment = BiasAlignment(horizontalBias = alignmentBias, verticalBias = 0f)
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
                imageVector = if (checked) Icons.Rounded.Check else Icons.Rounded.Close,
                contentDescription = null,
                tint = if (checked) ElectricCyan else OnSurfaceVariantSlate,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}
