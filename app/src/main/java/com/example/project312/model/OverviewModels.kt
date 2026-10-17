package com.example.project312.model

import androidx.compose.runtime.Immutable

@Immutable
data class HabitatTelemetry(
    val habitatName: String = "Nexus Habitat",
    val sectorName: String = "Living Sector Alpha",
    val overallStatus: String = "Optimal",
    val overallTemp: Double = 24.0,
    val ambientTemp: Double = 22.5,
    val humidityPercent: Int = 48,
    val aqi: Int = 14,
    val aqiStatus: String = "Optimal",
    val activeSensorsCount: Int = 18,
    val scrubbersRunning: Boolean = true,
    val airPurityPercent: Double = 99.4,
    val powerDemandKw: Double = 1.2
)

enum class SystemMode(val label: String) {
    HOME("Home"),
    AWAY("Away"),
    NIGHT_PROTOCOL("Night Protocol"),
    CINEMA_MODE("Cinema Mode"),
    PARTY_AURA("Party Aura")
}

enum class QuickActionIcon {
    LIGHT,
    CLIMATE,
    LOCK,
    FAN
}

enum class QuickActionAccent {
    CYAN,
    PURPLE,
    EMERALD
}

@Immutable
data class QuickActionItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: QuickActionIcon,
    val accent: QuickActionAccent,
    val isEnabled: Boolean
)

@Immutable
data class EnergyVectorTelemetry(
    val currentKwh: Double = 3.4,
    val deltaPercent: Int = -12,
    val timeLabels: List<String> = listOf("06:00", "12:00", "18:00", "Now"),
    // Normalized sample points (0.0f - 1.0f) representing the energy curve
    val splinePoints: List<Float> = listOf(0.25f, 0.40f, 0.65f, 0.50f, 0.85f)
)

enum class DeviceType {
    LIGHT,
    CLIMATE,
    AUDIO,
    BLINDS
}

enum class DeviceAccent {
    CYAN,
    CYAN_PURPLE_GRADIENT,
    PURPLE,
    EMERALD
}

@Immutable
data class SmartDeviceItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val badgeText: String,
    val level: Float, // 0.0f to 1.0f
    val deviceType: DeviceType,
    val accent: DeviceAccent,
    val isPlayingAudio: Boolean = false,
    val isPoweredOn: Boolean = true
)

enum class NavDestination(val label: String) {
    OVERVIEW("Overview"),
    ROOMS("Rooms"),
    ROUTINES("Routines"),
    ENERGY("Energy"),
    SETTINGS("Settings")
}

@Immutable
data class OverviewUiState(
    val telemetry: HabitatTelemetry = HabitatTelemetry(),
    val currentMode: SystemMode = SystemMode.HOME,
    val instantActions: List<QuickActionItem> = emptyList(),
    val energyVector: EnergyVectorTelemetry = EnergyVectorTelemetry(),
    val activeDevices: List<SmartDeviceItem> = emptyList(),
    val currentNavDestination: NavDestination = NavDestination.OVERVIEW,
    val hasUnreadNotifications: Boolean = true,
    val feedbackMessage: String? = null
)

sealed interface OverviewUiAction {
    data class ChangeMode(val mode: SystemMode) : OverviewUiAction
    data class ToggleInstantAction(val actionId: String) : OverviewUiAction
    data class ToggleDevicePower(val deviceId: String) : OverviewUiAction
    data class SetDeviceLevel(val deviceId: String, val level: Float) : OverviewUiAction
    data class SelectNavigation(val destination: NavDestination) : OverviewUiAction
    data object ToggleNotifications : OverviewUiAction
    data object OpenUserProfile : OverviewUiAction
    data object ClearFeedback : OverviewUiAction
}
