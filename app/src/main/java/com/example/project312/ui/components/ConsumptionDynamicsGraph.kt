package com.example.project312.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.ui.theme.AlertCrimson
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.OnBackgroundSlate50
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.VioletAurora

/**
 * 24-hr / Period Consumption Dynamics custom Bézier spline visualizer.
 * Displays peak telemetry callout and multi-gradient neon flow wave.
 */
@Composable
fun ConsumptionDynamicsGraph(
    peakLabel: String,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "peakPulse")
    val peakPulseFactor by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.30f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        ) {
            val w = size.width
            val h = size.height

            // 1. Reference dashed grid lines
            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            val gridColor = Color.White.copy(alpha = 0.07f)
            drawLine(
                color = gridColor,
                start = Offset(0f, h * 0.25f),
                end = Offset(w, h * 0.25f),
                strokeWidth = 1.dp.toPx(),
                pathEffect = dashEffect
            )
            drawLine(
                color = gridColor,
                start = Offset(0f, h * 0.65f),
                end = Offset(w, h * 0.65f),
                strokeWidth = 1.dp.toPx(),
                pathEffect = dashEffect
            )

            // 2. Exact Bézier Wave Curve mapped from spec
            // M 0,85 C 40,82 60,65 90,62 C 120,59 140,80 170,45 C 200,10 230,20 260,35 C 290,50 305,30 320,28
            val sx = w / 320f
            val sy = h / 100f

            val curvePath = Path().apply {
                moveTo(0f * sx, 85f * sy)
                cubicTo(
                    40f * sx, 82f * sy,
                    60f * sx, 65f * sy,
                    90f * sx, 62f * sy
                )
                cubicTo(
                    120f * sx, 59f * sy,
                    140f * sx, 80f * sy,
                    170f * sx, 45f * sy
                )
                cubicTo(
                    200f * sx, 10f * sy,
                    230f * sx, 20f * sy,
                    260f * sx, 35f * sy
                )
                cubicTo(
                    290f * sx, 50f * sy,
                    305f * sx, 30f * sy,
                    320f * sx, 28f * sy
                )
            }

            // 3. Translucent Under-Fill Area
            val areaPath = Path().apply {
                addPath(curvePath)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }

            val areaBrush = Brush.verticalGradient(
                colors = listOf(
                    ElectricCyan.copy(alpha = 0.35f),
                    ElectricCyan.copy(alpha = 0.10f),
                    Color.Transparent
                ),
                startY = 0f,
                endY = h
            )
            drawPath(path = areaPath, brush = areaBrush)

            // 4. Neon Gradient Stroke
            val strokeBrush = Brush.horizontalGradient(
                colors = listOf(
                    EcoEmeraldBright,
                    ElectricCyan,
                    VioletAurora,
                    ElectricCyan
                ),
                startX = 0f,
                endX = w
            )
            drawPath(
                path = curvePath,
                brush = strokeBrush,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )

            // 5. Peak Indicator Node at x = 200 * sx, y = 10 * sy
            val peakX = 200f * sx
            val peakY = 10f * sy

            // Outer glow ring
            drawCircle(
                color = ElectricCyan.copy(alpha = 0.40f),
                radius = 10.dp.toPx() * peakPulseFactor,
                center = Offset(peakX, peakY),
                style = Stroke(width = 1.5.dp.toPx())
            )
            // Inner solid core
            drawCircle(
                color = ElectricCyan,
                radius = 5.dp.toPx(),
                center = Offset(peakX, peakY)
            )
        }

        // Peak Floating Pill Callout
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(x = 30, y = -10) }
                .clip(RoundedCornerShape(999.dp))
                .background(SurfaceContainerHigh.copy(alpha = 0.90f))
                .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.PriorityHigh,
                    contentDescription = null,
                    tint = AlertCrimson,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = " $peakLabel",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        color = OnBackgroundSlate50
                    )
                )
            }
        }
    }
}
