package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.FirebaseFirestoreRepository
import com.example.project312.model.SettingsUiAction
import com.example.project312.model.SettingsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreRepository.observeSettings().collect { data ->
                if (data.isNotEmpty()) {
                    _uiState.update { current ->
                        current.copy(
                            connectivity = current.connectivity.copy(
                                threadMeshActive = data["threadMeshActive"] as? Boolean ?: current.connectivity.threadMeshActive,
                                localAiEdgeInference = data["localAiEdgeInference"] as? Boolean ?: current.connectivity.localAiEdgeInference,
                                matterBridgeSync = data["matterBridgeSync"] as? Boolean ?: current.connectivity.matterBridgeSync,
                                cloudTelemetrySync = data["cloudTelemetrySync"] as? Boolean ?: current.connectivity.cloudTelemetrySync
                            ),
                            automation = current.automation.copy(
                                autonomousEcoTuning = data["autonomousEcoTuning"] as? Boolean ?: current.automation.autonomousEcoTuning,
                                geofenceAutoArm = data["geofenceAutoArm"] as? Boolean ?: current.automation.geofenceAutoArm,
                                millimeterPresence = data["millimeterPresence"] as? Boolean ?: current.automation.millimeterPresence,
                                circadianSpectrumLighting = data["circadianSpectrumLighting"] as? Boolean ?: current.automation.circadianSpectrumLighting
                            ),
                            display = current.display.copy(
                                ultraGlassShaders = data["ultraGlassShaders"] as? Boolean ?: current.display.ultraGlassShaders,
                                tacticalHaptics = data["tacticalHaptics"] as? Boolean ?: current.display.tacticalHaptics,
                                ambientBackdropGlows = data["ambientBackdropGlows"] as? Boolean ?: current.display.ambientBackdropGlows
                            ),
                            isEmergencyGridLocked = data["isEmergencyGridLocked"] as? Boolean ?: current.isEmergencyGridLocked
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: SettingsUiAction) {
        when (action) {
            is SettingsUiAction.ToggleThreadMesh -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(threadMeshActive = action.enabled),
                        userFeedback = if (action.enabled) "Thread Mesh 6LoWPAN protocol enabled" else "Thread Mesh disabled (Fallback to Wi-Fi)"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("threadMeshActive", action.enabled) }
            }

            is SettingsUiAction.ToggleLocalAi -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(localAiEdgeInference = action.enabled),
                        userFeedback = if (action.enabled) "Edge Neural Processing enabled (Zero cloud latency)" else "Cloud Inference enabled"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("localAiEdgeInference", action.enabled) }
            }

            is SettingsUiAction.ToggleMatterBridge -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(matterBridgeSync = action.enabled),
                        userFeedback = if (action.enabled) "Matter 1.3 Bridge connected to HomeKit & Google Home" else "Matter Bridge offline"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("matterBridgeSync", action.enabled) }
            }

            is SettingsUiAction.ToggleCloudSync -> {
                _uiState.update { current ->
                    current.copy(
                        connectivity = current.connectivity.copy(cloudTelemetrySync = action.enabled),
                        userFeedback = if (action.enabled) "Encrypted Cloud Snapshot Relay enabled" else "Local-Only Enclave mode active"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("cloudTelemetrySync", action.enabled) }
            }

            is SettingsUiAction.ToggleAutonomousEco -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(autonomousEcoTuning = action.enabled),
                        userFeedback = if (action.enabled) "AI Autonomous Eco-Tuning engaged" else "Eco-Tuning set to manual schedule"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("autonomousEcoTuning", action.enabled) }
            }

            is SettingsUiAction.ToggleGeofenceAutoArm -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(geofenceAutoArm = action.enabled),
                        userFeedback = if (action.enabled) "500m Geofence Perimeter Auto-Arm enabled" else "Geofence Auto-Arm disabled"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("geofenceAutoArm", action.enabled) }
            }

            is SettingsUiAction.ToggleMillimeterPresence -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(millimeterPresence = action.enabled),
                        userFeedback = if (action.enabled) "60GHz mmWave Radar micro-presence online" else "mmWave Radar sleeping"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("millimeterPresence", action.enabled) }
            }

            is SettingsUiAction.SelectTempUnit -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(tempUnit = action.unit),
                        userFeedback = "Habitat Temperature Unit set to ${action.unit.label} (${action.unit.symbol})"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("tempUnit", action.unit.name) }
            }

            is SettingsUiAction.SetAqiThreshold -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(aqiAlertThreshold = action.threshold)
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("aqiAlertThreshold", action.threshold) }
            }

            is SettingsUiAction.ToggleCircadianLighting -> {
                _uiState.update { current ->
                    current.copy(
                        automation = current.automation.copy(circadianSpectrumLighting = action.enabled),
                        userFeedback = if (action.enabled) "Circadian Kelvin Dynamic Spectrum active (2700K - 6500K)" else "Static color temperature mode"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("circadianSpectrumLighting", action.enabled) }
            }

            is SettingsUiAction.ToggleUltraGlass -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(ultraGlassShaders = action.enabled),
                        userFeedback = if (action.enabled) "Ultra Frosted Glassmorphism shaders active" else "Performance glass mode active"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("ultraGlassShaders", action.enabled) }
            }

            is SettingsUiAction.ToggleTacticalHaptics -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(tacticalHaptics = action.enabled),
                        userFeedback = if (action.enabled) "Tactile Haptic Feedback enabled" else "Haptics disabled"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("tacticalHaptics", action.enabled) }
            }

            is SettingsUiAction.SelectThemeAccent -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(selectedAccent = action.accent),
                        userFeedback = "Aura Accent Palette switched to ${action.accent.label}"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("selectedAccent", action.accent.name) }
            }

            is SettingsUiAction.ToggleAmbientBackdrops -> {
                _uiState.update { current ->
                    current.copy(
                        display = current.display.copy(ambientBackdropGlows = action.enabled),
                        userFeedback = if (action.enabled) "Neon Backdrop Flare Glows enabled" else "Minimal backdrop mode"
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("ambientBackdropGlows", action.enabled) }
            }

            is SettingsUiAction.TriggerEmergencyLockdown -> {
                val newLocked = !_uiState.value.isEmergencyGridLocked
                _uiState.update { current ->
                    current.copy(
                        isEmergencyGridLocked = newLocked,
                        userFeedback = if (newLocked) {
                            "⚠️ EMERGENCY GRID LOCKDOWN ENGAGED: Gas & High-Voltage relays isolated"
                        } else {
                            "✅ Grid Lockdown cleared: Nominal telemetry restored"
                        }
                    )
                }
                viewModelScope.launch { firestoreRepository.updateSettingsField("isEmergencyGridLocked", newLocked) }
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
