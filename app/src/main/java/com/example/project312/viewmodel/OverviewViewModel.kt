package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.MockData
import com.example.project312.model.NavDestination
import com.example.project312.model.OverviewUiAction
import com.example.project312.model.OverviewUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MockData.createInitialOverviewState())
    val uiState: StateFlow<OverviewUiState> = _uiState.asStateFlow()

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
                _uiState.update { current ->
                    val updatedActions = current.instantActions.map { item ->
                        if (item.id == action.actionId) {
                            val nextState = !item.isEnabled
                            item.copy(isEnabled = nextState)
                        } else {
                            item
                        }
                    }
                    val target = current.instantActions.find { it.id == action.actionId }
                    val stateText = if (target?.isEnabled == false) "Engaged" else "Disengaged"
                    current.copy(
                        instantActions = updatedActions,
                        feedbackMessage = "${target?.title ?: "Action"}: $stateText"
                    )
                }
            }

            is OverviewUiAction.ToggleDevicePower -> {
                _uiState.update { current ->
                    val updatedDevices = current.activeDevices.map { device ->
                        if (device.id == action.deviceId) {
                            val nextState = !device.isPoweredOn
                            device.copy(isPoweredOn = nextState)
                        } else {
                            device
                        }
                    }
                    val target = current.activeDevices.find { it.id == action.deviceId }
                    val stateText = if (target?.isPoweredOn == false) "Powered On" else "Powered Down"
                    current.copy(
                        activeDevices = updatedDevices,
                        feedbackMessage = "${target?.name ?: "Device"}: $stateText"
                    )
                }
            }

            is OverviewUiAction.SetDeviceLevel -> {
                _uiState.update { current ->
                    val clampedLevel = action.level.coerceIn(0f, 1f)
                    val updatedDevices = current.activeDevices.map { device ->
                        if (device.id == action.deviceId) {
                            val badge = when (device.id) {
                                "climate_pod" -> "Cooling"
                                "soundwave_ultra" -> "Lofi"
                                else -> "${(clampedLevel * 100).toInt()}%"
                            }
                            device.copy(level = clampedLevel, badgeText = badge)
                        } else {
                            device
                        }
                    }
                    current.copy(activeDevices = updatedDevices)
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
