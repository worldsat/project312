package com.example.project312.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EnergySavingsLeaf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project312.ui.theme.EcoEmeraldBright
import com.example.project312.ui.theme.ElectricCyan
import com.example.project312.ui.theme.ElectricCyanFixedDim
import com.example.project312.ui.theme.OnSurfaceVariantSlate
import com.example.project312.ui.theme.SurfaceContainerHigh
import com.example.project312.ui.theme.VioletAurora

/**
 * Concentric Dual-Arc Power Flux Gauge specified in DESIGN.md Section 3.5.
 * Features an outer gradient demand arc, an inner ghost secondary ring, and central telemetry.
 */
@Composable
fun PowerFluxGauge(
    totalDemandKw: Double,
    selfSustainedPercent: Int,
    demandFraction: Float,
    ghostFraction: Float,
    modifier: Modifier = Modifier,
    isOptimized: Boolean = false
) {
    val animatedDemandFraction by animateFloatAsState(
        targetValue = demandFraction,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing),
        label = "demandArc"
    )

    Box(
        modifier = modifier.size(240.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(240.dp)) {
            val strokeWidthPx = 10.dp.toPx()
            val innerStrokeWidthPx = 4.dp.toPx()
            val diameter = size.minDimension - strokeWidthPx
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val arcTopLeft = Offset(center.x - radius, center.y - radius)
            val arcSize = Size(diameter, diameter)

            // 1. Inactive base circular track (360°)
            drawArc(
                color = Color.White.copy(alpha = 0.08f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            // 2. Active Demand Arc with Gradient (rotating starting at -90°)
            val sweep = 360f * animatedDemandFraction
            val activeArcBrush = Brush.sweepGradient(
                colors = listOf(
                    ElectricCyan,
                    ElectricCyanFixedDim,
                    EcoEmeraldBright,
                    ElectricCyan
                ),
                center = center
            )

            // Outer soft glow aura
            drawArc(
                brush = activeArcBrush,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx + 4.dp.toPx(), cap = StrokeCap.Round),
                alpha = 0.35f
            )

            // Primary sharp arc
            drawArc(
                brush = activeArcBrush,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            // 3. Inner ghost secondary ring (Violet / Cyan draw marker)
            val innerRadius = radius - 16.dp.toPx()
            val innerTopLeft = Offset(center.x - innerRadius, center.y - innerRadius)
            val innerSize = Size(innerRadius * 2f, innerRadius * 2f)

            drawArc(
                color = VioletAurora.copy(alpha = 0.22f),
                startAngle = -90f,
                sweepAngle = 360f * ghostFraction,
                useCenter = false,
                topLeft = innerTopLeft,
                size = innerSize,
                style = Stroke(width = innerStrokeWidthPx, cap = StrokeCap.Round)
            )
        }

        // Central Telemetry Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOTAL DEMAND",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    color = OnSurfaceVariantSlate
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = String.format("%.2f", totalDemandKw),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isOptimized) EcoEmeraldBright else Color(0xFFE0FDFF)
                    )
                )
                Text(
                    text = " kW",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyanFixedDim
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Self-Sustained Pill Chip
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(SurfaceContainerHigh.copy(alpha = 0.70f))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.EnergySavingsLeaf,
                    contentDescription = null,
                    tint = EcoEmeraldBright,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = " $selfSustainedPercent% Self-Sustained",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = EcoEmeraldBright
                    )
                )
            }
        }
    }
}
