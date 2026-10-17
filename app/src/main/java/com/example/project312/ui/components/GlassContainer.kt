package com.example.project312.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.project312.ui.theme.GlassBorderEnd
import com.example.project312.ui.theme.GlassBorderStart
import com.example.project312.ui.theme.SurfaceContainerLow
import com.example.project312.ui.theme.SurfaceObsidian

/**
 * Frosted Obsidian Glassmorphic Container as specified in DESIGN.md Section 3.3.
 * Features a vertical dark gradient and delicate 1dp linear gradient rim border.
 */
@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    paddingValues: PaddingValues = PaddingValues(16.dp),
    shape: Shape = RoundedCornerShape(cornerRadius),
    backgroundBrush: Brush = Brush.verticalGradient(
        colors = listOf(
            SurfaceContainerLow.copy(alpha = 0.75f),
            SurfaceObsidian.copy(alpha = 0.85f)
        )
    ),
    borderBrush: Brush = Brush.linearGradient(
        colors = listOf(
            GlassBorderStart,
            GlassBorderEnd
        )
    ),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = Color.White.copy(alpha = 0.1f)),
            onClick = onClick
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(brush = backgroundBrush, shape = shape)
            .border(width = 1.dp, brush = borderBrush, shape = shape)
            .then(clickableModifier)
            .padding(paddingValues),
        content = content
    )
}
