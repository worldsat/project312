package com.example.project312.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.VioletContainerVibrant

/**
 * Custom Canvas Bézier curve visualizer for the Energy Vector section.
 * Faithful reproduction of the SVG telemetry wave from DESIGN.md and screen.png.
 */
@Composable
fun EnergyVectorGraph(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseRadiusFactor by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseRadius"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        val w = size.width
        val h = size.height

        // 1. Reference dashed grid lines
        val dashEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
        val gridLineColor = Color.White.copy(alpha = 0.08f)

        val gridY1 = h * 0.25f
        val gridY2 = h * 0.65f
        drawLine(
            color = gridLineColor,
            start = Offset(0f, gridY1),
            end = Offset(w, gridY1),
            pathEffect = dashEffect,
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = gridLineColor,
            start = Offset(0f, gridY2),
            end = Offset(w, gridY2),
            pathEffect = dashEffect,
            strokeWidth = 1.dp.toPx()
        )

        // 2. Control points mapped from the design spec (normalized)
        // M0,45 Q40,38 80,30 T160,20 T240,32 T320,12
        val y0 = h * (45f / 60f)
        val y1 = h * (30f / 60f)
        val y2 = h * (20f / 60f)
        val y3 = h * (32f / 60f)
        val yEnd = h * (12f / 60f)

        val curvePath = Path().apply {
            moveTo(0f, y0)
            cubicTo(
                w * 0.125f, h * (38f / 60f),
                w * 0.18f, y1,
                w * 0.25f, y1
            )
            cubicTo(
                w * 0.35f, y1,
                w * 0.42f, y2,
                w * 0.50f, y2
            )
            cubicTo(
                w * 0.60f, y2,
                w * 0.68f, y3,
                w * 0.75f, y3
            )
            cubicTo(
                w * 0.85f, y3,
                w * 0.92f, yEnd,
                w, yEnd
            )
        }

        // 3. Area gradient under curve
        val areaPath = Path().apply {
            addPath(curvePath)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        val areaGradient = Brush.verticalGradient(
            colors = listOf(
                ElectricCyan.copy(alpha = 0.28f),
                ElectricCyan.copy(alpha = 0.08f),
                Color.Transparent
            ),
            startY = 0f,
            endY = h
        )
        drawPath(path = areaPath, brush = areaGradient)

        // 4. Glowing Stroke
        val curveGradient = Brush.horizontalGradient(
            colors = listOf(
                ElectricCyan.copy(alpha = 0.9f),
                VioletContainerVibrant.copy(alpha = 0.95f),
                EcoEmeraldBright
            ),
            startX = 0f,
            endX = w
        )

        drawPath(
            path = curvePath,
            brush = curveGradient,
            style = Stroke(
                width = 3.2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        // 5. Active Marker ("Now")
        val markerCenter = Offset(w, yEnd)
        // Outer glowing halo
        drawCircle(
            color = EcoEmeraldBright.copy(alpha = 0.30f),
            radius = 10.dp.toPx() * pulseRadiusFactor,
            center = markerCenter
        )
        // Inner glowing core
        drawCircle(
            color = EcoEmeraldBright,
            radius = 4.5.dp.toPx(),
            center = markerCenter
        )
    }
}
