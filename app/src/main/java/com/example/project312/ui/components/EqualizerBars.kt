package com.example.project312.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.project312.ui.theme.VioletAurora

/**
 * Animated Equalizer bars for audio status telemetry (SoundWave Ultra).
 */
@Composable
fun EqualizerBars(
    modifier: Modifier = Modifier,
    isAnimating: Boolean = true,
    barColor: Color = VioletAurora,
    barWidth: Dp = 2.5.dp,
    maxHeight: Dp = 16.dp
) {
    val transition = rememberInfiniteTransition(label = "equalizerTransition")

    val heightFactor1 by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar1"
    )

    val heightFactor2 by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 0.40f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar2"
    )

    val heightFactor3 by transition.animateFloat(
        initialValue = 0.40f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 520, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar3"
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.5.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        val f1 = if (isAnimating) heightFactor1 else 0.4f
        val f2 = if (isAnimating) heightFactor2 else 0.7f
        val f3 = if (isAnimating) heightFactor3 else 0.5f

        Box(
            modifier = Modifier
                .width(barWidth)
                .height(maxHeight * f1)
                .clip(RoundedCornerShape(999.dp))
                .background(barColor)
        )
        Box(
            modifier = Modifier
                .width(barWidth)
                .height(maxHeight * f2)
                .clip(RoundedCornerShape(999.dp))
                .background(barColor)
        )
        Box(
            modifier = Modifier
                .width(barWidth)
                .height(maxHeight * f3)
                .clip(RoundedCornerShape(999.dp))
                .background(barColor)
        )
    }
}
