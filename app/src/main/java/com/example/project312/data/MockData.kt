package com.example.project312.data

import com.example.project312.model.DeviceAccent
import com.example.project312.model.DeviceType
import com.example.project312.model.EnergyVectorTelemetry
import com.example.project312.model.HabitatTelemetry
import com.example.project312.model.NavDestination
import com.example.project312.model.OverviewUiState
import com.example.project312.model.QuickActionAccent
import com.example.project312.model.QuickActionIcon
import com.example.project312.model.QuickActionItem
import com.example.project312.model.SmartDeviceItem
import com.example.project312.model.SystemMode

object MockData {

    val initialTelemetry = HabitatTelemetry(
        habitatName = "Nexus Habitat",
        sectorName = "Living Sector Alpha",
        overallStatus = "Optimal",
        overallTemp = 24.0,
        ambientTemp = 22.5,
        humidityPercent = 48,
        aqi = 14,
        aqiStatus = "Optimal",
        activeSensorsCount = 18,
        scrubbersRunning = true,
        airPurityPercent = 99.4,
        powerDemandKw = 1.2
    )

    val initialInstantActions = listOf(
        QuickActionItem(
            id = "ambient_glow",
            title = "Ambient Glow",
            subtitle = "Atmosphere 4000K",
            icon = QuickActionIcon.LIGHT,
            accent = QuickActionAccent.CYAN,
            isEnabled = true
        ),
        QuickActionItem(
            id = "climate_units",
            title = "All Climate Units",
            subtitle = "Preset 21.0°C",
            icon = QuickActionIcon.CLIMATE,
            accent = QuickActionAccent.CYAN,
            isEnabled = true
        ),
        QuickActionItem(
            id = "lock_doors",
            title = "Lock All Doors",
            subtitle = "Biometric Perimeter",
            icon = QuickActionIcon.LOCK,
            accent = QuickActionAccent.PURPLE,
            isEnabled = true
        ),
        QuickActionItem(
            id = "turbo_purify",
            title = "Turbo Purify",
            subtitle = "Standby Mode",
            icon = QuickActionIcon.FAN,
            accent = QuickActionAccent.EMERALD,
            isEnabled = false
        )
    )

    val initialEnergyVector = EnergyVectorTelemetry(
        currentKwh = 3.4,
        deltaPercent = -12,
        timeLabels = listOf("06:00", "12:00", "18:00", "Now"),
        // Normalized Y coordinates (0.0 = bottom, 1.0 = top)
        // Matching curve points: starts low (0.25), climbs (0.40, 0.65), sags gently (0.50), peaks up to (0.85) at Now
        splinePoints = listOf(0.25f, 0.38f, 0.66f, 0.48f, 0.85f)
    )

    val initialDevices = listOf(
        SmartDeviceItem(
            id = "cyberlight_pro",
            name = "CyberLight Pro",
            subtitle = "Living • Cyan Tint",
            badgeText = "75%",
            level = 0.75f,
            deviceType = DeviceType.LIGHT,
            accent = DeviceAccent.CYAN,
            isPoweredOn = true
        ),
        SmartDeviceItem(
            id = "climate_pod",
            name = "Climate Pod",
            subtitle = "Target 21.5°C",
            badgeText = "Cooling",
            level = 0.65f,
            deviceType = DeviceType.CLIMATE,
            accent = DeviceAccent.CYAN_PURPLE_GRADIENT,
            isPoweredOn = true
        ),
        SmartDeviceItem(
            id = "soundwave_ultra",
            name = "SoundWave Ultra",
            subtitle = "Cyberpunk Lofi",
            badgeText = "Lofi",
            level = 0.60f,
            deviceType = DeviceType.AUDIO,
            accent = DeviceAccent.PURPLE,
            isPlayingAudio = true,
            isPoweredOn = true
        ),
        SmartDeviceItem(
            id = "smart_blinds",
            name = "Smart Blinds",
            subtitle = "East Facade Open",
            badgeText = "80%",
            level = 0.80f,
            deviceType = DeviceType.BLINDS,
            accent = DeviceAccent.EMERALD,
            isPoweredOn = true
        )
    )

    fun createInitialOverviewState(): OverviewUiState {
        return OverviewUiState(
            telemetry = initialTelemetry,
            currentMode = SystemMode.HOME,
            instantActions = initialInstantActions,
            energyVector = initialEnergyVector,
            activeDevices = initialDevices,
            currentNavDestination = NavDestination.OVERVIEW,
            hasUnreadNotifications = true,
            feedbackMessage = null
        )
    }
}
