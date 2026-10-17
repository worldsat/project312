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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.project312.model.QuickActionAccent
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanDark
import com.example.project312.ui.theme.EcoEmerald
import com.example.project312.ui.theme.SurfaceContainerHighest
import com.example.project312.ui.theme.VioletContainerVibrant

/**
 * Interactive Neon Pill Switch conforming to Section 3.4 of DESIGN.md.
 * Features animated knob transition, glowing colored track when ON, and recessed dark look when OFF.
 */
@Composable
fun NeonPillSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    accent: QuickActionAccent = QuickActionAccent.CYAN,
    width: Dp = 48.dp,
    height: Dp = 26.dp,
    knobSize: Dp = 20.dp
) {
    val activeTrackColor = when (accent) {
        QuickActionAccent.CYAN -> ElectricCyan
        QuickActionAccent.PURPLE -> VioletContainerVibrant
        QuickActionAccent.EMERALD -> EcoEmerald
    }

    val trackColor by animateColorAsState(
        targetValue = if (checked) activeTrackColor else SurfaceContainerHighest,
        label = "switchTrackColor"
    )

    val knobColor by animateColorAsState(
        targetValue = if (checked) ElectricCyanDark else Color(0xFF849495),
        label = "switchKnobColor"
    )

    val alignmentBias by animateFloatAsState(
        targetValue = if (checked) 1f else -1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "switchKnobBias"
    )

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(999.dp))
            .background(trackColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCheckedChange(!checked)
            }
            .padding(horizontal = 3.dp),
        contentAlignment = BiasAlignment(horizontalBias = alignmentBias, verticalBias = 0f)
    ) {
        Box(
            modifier = Modifier
                .size(knobSize)
                .shadow(
                    elevation = if (checked) 4.dp else 1.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(knobColor)
        )
    }
}
