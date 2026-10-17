package com.example.project312.data

import com.example.project312.model.AutomationPreferences
import com.example.project312.model.ConnectivitySettings
import com.example.project312.model.CyberThemeAccent
import com.example.project312.model.HabitatCoreInfo
import com.example.project312.model.InterfacePreferences
import com.example.project312.model.MeshSecurityLevel
import com.example.project312.model.SettingsUiState
import com.example.project312.model.TemperatureUnit

object SettingsMockData {
    fun createInitialState(): SettingsUiState = SettingsUiState(
        coreInfo = HabitatCoreInfo(
            hubId = "NEXUS-ALPHA-77",
            firmwareVersion = "v4.8.2-Quantum",
            meshNodesConnected = 28,
            networkLatencyMs = 2,
            hubHealthPercent = 100,
            uptimeDays = 142,
            securityLevel = MeshSecurityLevel.QUANTUM_256
        ),
        connectivity = ConnectivitySettings(
            threadMeshActive = true,
            localAiEdgeInference = true,
            matterBridgeSync = true,
            zigbeeChannel = 25,
            cloudTelemetrySync = false
        ),
        automation = AutomationPreferences(
            autonomousEcoTuning = true,
            geofenceAutoArm = true,
            millimeterPresence = true,
            tempUnit = TemperatureUnit.CELSIUS,
            aqiAlertThreshold = 100,
            circadianSpectrumLighting = true
        ),
        display = InterfacePreferences(
            ultraGlassShaders = true,
            tacticalHaptics = true,
            selectedAccent = CyberThemeAccent.ELECTRIC_CYAN,
            ambientBackdropGlows = true
        ),
        isEmergencyGridLocked = false,
        isDiagnosticRunning = false,
        userFeedback = null
    )
}
