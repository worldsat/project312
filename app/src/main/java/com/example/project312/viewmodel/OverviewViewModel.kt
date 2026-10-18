package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.FirebaseFirestoreRepository
import com.example.project312.model.DeviceAccent
import com.example.project312.model.DeviceType
import com.example.project312.model.NavDestination
import com.example.project312.model.OverviewUiAction
import com.example.project312.model.OverviewUiState
import com.example.project312.model.QuickActionAccent
import com.example.project312.model.QuickActionIcon
import com.example.project312.model.QuickActionItem
import com.example.project312.model.SmartDeviceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private val initialInstantActions = listOf(
        QuickActionItem("ambient_glow", "Ambient Glow", "Atmosphere 4000K", QuickActionIcon.LIGHT, QuickActionAccent.CYAN, true),
        QuickActionItem("climate_units", "All Climate Units", "Preset 21.0°C", QuickActionIcon.CLIMATE, QuickActionAccent.CYAN, true),
        QuickActionItem("lock_doors", "Lock All Doors", "Biometric Perimeter", QuickActionIcon.LOCK, QuickActionAccent.PURPLE, true),
        QuickActionItem("turbo_purify", "Turbo Purify", "Standby Mode", QuickActionIcon.FAN, QuickActionAccent.EMERALD, false)
    )

    private val initialDevices = listOf(
        SmartDeviceItem("cyberlight_pro", "CyberLight Pro", "Living • Cyan Tint", "75%", 0.75f, DeviceType.LIGHT, DeviceAccent.CYAN, isPoweredOn = true),
        SmartDeviceItem("climate_pod", "Climate Pod", "Target 21.5°C", "Cooling", 0.65f, DeviceType.CLIMATE, DeviceAccent.CYAN_PURPLE_GRADIENT, isPoweredOn = true),
        SmartDeviceItem("soundwave_ultra", "SoundWave Ultra", "Cyberpunk Lofi", "Lofi", 0.60f, DeviceType.AUDIO, DeviceAccent.PURPLE, isPlayingAudio = true, isPoweredOn = true),
        SmartDeviceItem("smart_blinds", "Smart Blinds", "East Facade Open", "80%", 0.80f, DeviceType.BLINDS, DeviceAccent.EMERALD, isPoweredOn = true)
    )

    private val _uiState = MutableStateFlow(
        OverviewUiState(
            instantActions = initialInstantActions,
            activeDevices = initialDevices
        )
    )
    val uiState: StateFlow<OverviewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreRepository.seedInitialDataIfEmpty()
        }
        viewModelScope.launch {
            firestoreRepository.observeTelemetry().collect { cloudTelemetry ->
                _uiState.update { it.copy(telemetry = cloudTelemetry) }
            }
        }
        viewModelScope.launch {
            firestoreRepository.observeInstantActions().collect { cloudActions ->
                if (cloudActions.isNotEmpty()) {
                    _uiState.update { it.copy(instantActions = cloudActions) }
                }
            }
        }
        viewModelScope.launch {
            firestoreRepository.observeDevices().collect { cloudDevices ->
                if (cloudDevices.isNotEmpty()) {
                    _uiState.update { it.copy(activeDevices = cloudDevices) }
                }
            }
        }
    }

    fun onAction(action: OverviewUiAction) {
        when (action) {
            is OverviewUiAction.ChangeMode -> {
                _uiState.update { current ->
                    current.copy(
                        currentMode = action.mode,
                        feedbackMessage = "System Mode shifted to: ${action.mode.label}"
                    )
                }
            }

            is OverviewUiAction.ToggleInstantAction -> {
                val target = _uiState.value.instantActions.find { it.id == action.actionId }
                val nextState = if (target?.isEnabled == true) false else true
                _uiState.update { current ->
                    val updatedActions = current.instantActions.map { item ->
                        if (item.id == action.actionId) {
                            item.copy(isEnabled = nextState)
                        } else {
                            item
                        }
                    }
                    val stateText = if (nextState) "Engaged" else "Disengaged"
                    current.copy(
                        instantActions = updatedActions,
                        feedbackMessage = "${target?.title ?: "Action"}: $stateText"
                    )
                }
                viewModelScope.launch {
                    firestoreRepository.updateInstantAction(action.actionId, nextState)
                }
            }

            is OverviewUiAction.ToggleDevicePower -> {
                val target = _uiState.value.activeDevices.find { it.id == action.deviceId }
                val nextState = if (target?.isPoweredOn == true) false else true
                _uiState.update { current ->
                    val updatedDevices = current.activeDevices.map { device ->
                        if (device.id == action.deviceId) {
                            device.copy(isPoweredOn = nextState)
                        } else {
                            device
                        }
                    }
                    val stateText = if (nextState) "Powered On" else "Powered Down"
                    current.copy(
                        activeDevices = updatedDevices,
                        feedbackMessage = "${target?.name ?: "Device"}: $stateText"
                    )
                }
                viewModelScope.launch {
                    firestoreRepository.updateDevicePower(action.deviceId, nextState)
                }
            }

            is OverviewUiAction.SetDeviceLevel -> {
                val clampedLevel = action.level.coerceIn(0f, 1f)
                val badge = when (action.deviceId) {
                    "climate_pod" -> "Cooling"
                    "soundwave_ultra" -> "Lofi"
                    else -> "${(clampedLevel * 100).toInt()}%"
                }
                _uiState.update { current ->
                    val updatedDevices = current.activeDevices.map { device ->
                        if (device.id == action.deviceId) {
                            device.copy(level = clampedLevel, badgeText = badge)
                        } else {
                            device
                        }
                    }
                    current.copy(activeDevices = updatedDevices)
                }
                viewModelScope.launch {
                    firestoreRepository.updateDeviceLevel(action.deviceId, clampedLevel, badge)
                }
            }

            is OverviewUiAction.SelectNavigation -> {
                _uiState.update { current ->
                    if (action.destination == NavDestination.SETTINGS) {
                        current.copy(
                            feedbackMessage = "Nexus Habitat Core Settings (Local Demo Mode)"
                        )
                    } else {
                        current.copy(
                            currentNavDestination = action.destination
                        )
                    }
                }
            }

            is OverviewUiAction.ToggleNotifications -> {
                _uiState.update { current ->
                    val newUnreadState = !current.hasUnreadNotifications
                    current.copy(
                        hasUnreadNotifications = newUnreadState,
                        feedbackMessage = if (newUnreadState) "1 Telemetry Alert pending" else "Notifications cleared"
                    )
                }
            }

            is OverviewUiAction.OpenUserProfile -> {
                _uiState.update { current ->
                    current.copy(
                        feedbackMessage = "Habitat Administrator: Commander Jamali"
                    )
                }
            }

            is OverviewUiAction.ClearFeedback -> {
                _uiState.update { it.copy(feedbackMessage = null) }
            }
        }
    }
}
