package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.SettingsMockData
import com.example.project312.model.SettingsUiAction
import com.example.project312.model.SettingsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsMockData.createInitialState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun onAction(action: SettingsUiAction) {
        when (action) {
            is SettingsUiAction.ToggleThreadMesh -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(threadMeshActive = action.enabled),
                        userFeedback = if (action.enabled) "Thread Mesh 6LoWPAN protocol enabled" else "Thread Mesh disabled (Fallback to Wi-Fi)"
                    )
                }
            }

            is SettingsUiAction.ToggleLocalAi -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(localAiEdgeInference = action.enabled),
                        userFeedback = if (action.enabled) "Edge Neural Processing enabled (Zero cloud latency)" else "Cloud Inference enabled"
                    )
                }
            }

            is SettingsUiAction.ToggleMatterBridge -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(matterBridgeSync = action.enabled),
                        userFeedback = if (action.enabled) "Matter 1.3 Bridge connected to HomeKit & Google Home" else "Matter Bridge offline"
                    )
                }
            }

            is SettingsUiAction.ToggleCloudSync -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(cloudTelemetrySync = action.enabled),
                        userFeedback = if (action.enabled) "Encrypted Cloud Snapshot Relay enabled" else "Local-Only Enclave mode active"
                    )
                }
            }

            is SettingsUiAction.ToggleAutonomousEco -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(autonomousEcoTuning = action.enabled),
                        userFeedback = if (action.enabled) "AI Autonomous Eco-Tuning engaged" else "Eco-Tuning set to manual schedule"
                    )
                }
            }

            is SettingsUiAction.ToggleGeofenceAutoArm -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(geofenceAutoArm = action.enabled),
                        userFeedback = if (action.enabled) "500m Geofence Perimeter Auto-Arm enabled" else "Geofence Auto-Arm disabled"
                    )
                }
            }

            is SettingsUiAction.ToggleMillimeterPresence -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(millimeterPresence = action.enabled),
                        userFeedback = if (action.enabled) "60GHz mmWave Radar micro-presence online" else "mmWave Radar sleeping"
                    )
                }
            }

            is SettingsUiAction.SelectTempUnit -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(tempUnit = action.unit),
                        userFeedback = "Habitat Temperature Unit set to ${action.unit.label} (${action.unit.symbol})"
                    )
                }
            }

            is SettingsUiAction.SetAqiThreshold -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(aqiAlertThreshold = action.threshold)
                    )
                }
            }

            is SettingsUiAction.ToggleCircadianLighting -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(circadianSpectrumLighting = action.enabled),
                        userFeedback = if (action.enabled) "Circadian Kelvin Dynamic Spectrum active (2700K - 6500K)" else "Static color temperature mode"
                    )
                }
            }

            is SettingsUiAction.ToggleUltraGlass -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(ultraGlassShaders = action.enabled),
                        userFeedback = if (action.enabled) "Ultra Frosted Glassmorphism shaders active" else "Performance glass mode active"
                    )
                }
            }

            is SettingsUiAction.ToggleTacticalHaptics -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(tacticalHaptics = action.enabled),
                        userFeedback = if (action.enabled) "Tactile Haptic Feedback enabled" else "Haptics disabled"
                    )
                }
            }

            is SettingsUiAction.SelectThemeAccent -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(selectedAccent = action.accent),
                        userFeedback = "Aura Accent Palette switched to ${action.accent.label}"
                    )
                }
            }

            is SettingsUiAction.ToggleAmbientBackdrops -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(ambientBackdropGlows = action.enabled),
                        userFeedback = if (action.enabled) "Neon Backdrop Flare Glows enabled" else "Minimal backdrop mode"
                    )
                }
            }

            is SettingsUiAction.TriggerEmergencyLockdown -> {
                _uiState.update { current ->
                    val newLocked = !current.isEmergencyGridLocked
                    current.copy(
                        isEmergencyGridLocked = newLocked,
                        userFeedback = if (newLocked) {
                            "⚠️ EMERGENCY GRID LOCKDOWN ENGAGED: Gas & High-Voltage relays isolated"
                        } else {
                            "✅ Grid Lockdown cleared: Nominal telemetry restored"
                        }
                    )
                }
            }

            is SettingsUiAction.RunSystemDiagnostic -> {
                _uiState.update { it.copy(isDiagnosticRunning = true, userFeedback = "Pinging 28 Habitat Mesh Nodes...") }
                viewModelScope.launch {
                    delay(1200)
                    _uiState.update { current ->
                        current.copy(
                            isDiagnosticRunning = false,
                            userFeedback = "Self-Diagnostic Complete: 28/28 Nodes Online • Latency 1.6ms • Mesh 100% Nominal"
                        )
                    }
                }
            }

            is SettingsUiAction.ClearFeedback -> {
                _uiState.update { it.copy(userFeedback = null) }
            }
        }
    }
}
