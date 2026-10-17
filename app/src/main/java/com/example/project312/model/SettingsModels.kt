package com.example.project312.model

import androidx.compose.runtime.Immutable

enum class TemperatureUnit(val symbol: String, val label: String) {
    CELSIUS("°C", "Celsius"),
    FAHRENHEIT("°F", "Fahrenheit")
}

enum class CyberThemeAccent(val id: String, val label: String, val hexCode: Long) {
    ELECTRIC_CYAN("cyan", "Electric Cyan", 0xFF00F2FE),
    VIOLET_AURORA("violet", "Violet Aurora", 0xFF8B5CF6),
    ECO_EMERALD("emerald", "Eco Emerald", 0xFF10B981)
}

enum class MeshSecurityLevel(val label: String, val encryption: String) {
    QUANTUM_256("Quantum Mesh", "AES-256-GCM"),
    EDGE_GUARD("Edge Local", "Hardware Enclave"),
    LEGACY_COMPAT("Standard", "TLS 1.3")
}

@Immutable
data class HabitatCoreInfo(
    val hubId: String = "NEXUS-ALPHA-77",
    val firmwareVersion: String = "v4.8.2-Quantum",
    val meshNodesConnected: Int = 28,
    val networkLatencyMs: Int = 2,
    val hubHealthPercent: Int = 100,
    val uptimeDays: Int = 142,
    val securityLevel: MeshSecurityLevel = MeshSecurityLevel.QUANTUM_256
)

@Immutable
data class ConnectivitySettings(
    val threadMeshActive: Boolean = true,
    val localAiEdgeInference: Boolean = true,
    val matterBridgeSync: Boolean = true,
    val zigbeeChannel: Int = 25,
    val cloudTelemetrySync: Boolean = false
)

@Immutable
data class AutomationPreferences(
    val autonomousEcoTuning: Boolean = true,
    val geofenceAutoArm: Boolean = true,
    val millimeterPresence: Boolean = true,
    val tempUnit: TemperatureUnit = TemperatureUnit.CELSIUS,
    val aqiAlertThreshold: Int = 100,
    val circadianSpectrumLighting: Boolean = true
)

@Immutable
data class InterfacePreferences(
    val ultraGlassShaders: Boolean = true,
    val tacticalHaptics: Boolean = true,
    val selectedAccent: CyberThemeAccent = CyberThemeAccent.ELECTRIC_CYAN,
    val ambientBackdropGlows: Boolean = true
)

@Immutable
data class SettingsUiState(
    val coreInfo: HabitatCoreInfo = HabitatCoreInfo(),
    val connectivity: ConnectivitySettings = ConnectivitySettings(),
    val automation: AutomationPreferences = AutomationPreferences(),
    val display: InterfacePreferences = InterfacePreferences(),
    val isEmergencyGridLocked: Boolean = false,
    val isDiagnosticRunning: Boolean = false,
    val userFeedback: String? = null
)

sealed interface SettingsUiAction {
    data class ToggleThreadMesh(val enabled: Boolean) : SettingsUiAction
    data class ToggleLocalAi(val enabled: Boolean) : SettingsUiAction
    data class ToggleMatterBridge(val enabled: Boolean) : SettingsUiAction
    data class ToggleCloudSync(val enabled: Boolean) : SettingsUiAction

    data class ToggleAutonomousEco(val enabled: Boolean) : SettingsUiAction
    data class ToggleGeofenceAutoArm(val enabled: Boolean) : SettingsUiAction
    data class ToggleMillimeterPresence(val enabled: Boolean) : SettingsUiAction
    data class SelectTempUnit(val unit: TemperatureUnit) : SettingsUiAction
    data class SetAqiThreshold(val threshold: Int) : SettingsUiAction
    data class ToggleCircadianLighting(val enabled: Boolean) : SettingsUiAction

    data class ToggleUltraGlass(val enabled: Boolean) : SettingsUiAction
    data class ToggleTacticalHaptics(val enabled: Boolean) : SettingsUiAction
    data class SelectThemeAccent(val accent: CyberThemeAccent) : SettingsUiAction
    data class ToggleAmbientBackdrops(val enabled: Boolean) : SettingsUiAction

    data object TriggerEmergencyLockdown : SettingsUiAction
    data object RunSystemDiagnostic : SettingsUiAction
    data object ClearFeedback : SettingsUiAction
}
