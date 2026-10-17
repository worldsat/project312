package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project312.data.RoomMockData
import com.example.project312.model.RoomCategory
import com.example.project312.model.RoomFilter
import com.example.project312.model.RoomsUiAction
import com.example.project312.model.RoomsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoomsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoomMockData.createInitialState())
    val uiState: StateFlow<RoomsUiState> = _uiState.asStateFlow()

    fun onAction(action: RoomsUiAction) {
        when (action) {
            is RoomsUiAction.SelectFilter -> {
                _uiState.update { current ->
                    val filteredRooms = when (action.filter) {
                        RoomFilter.ALL -> RoomMockData.initialRooms
                        RoomFilter.LIVING_ROOM -> RoomMockData.initialRooms.filter { it.category == RoomCategory.LIVING_ROOM }
                        RoomFilter.CYBER_KITCHEN -> RoomMockData.initialRooms.filter { it.category == RoomCategory.CYBER_KITCHEN }
                        RoomFilter.MASTER_SUITE -> RoomMockData.initialRooms.filter { it.category == RoomCategory.MASTER_SUITE }
                        RoomFilter.HOLODECK_STUDIO -> RoomMockData.initialRooms.filter { it.category == RoomCategory.HOLODECK_STUDIO }
                        RoomFilter.PATIO -> emptyList()
                    }
                    current.copy(
                        selectedFilter = action.filter,
                        rooms = filteredRooms,
                        userFeedback = "Viewing ${action.filter.label}"
                    )
                }
            }

            is RoomsUiAction.ToggleRoomPower -> {
                _uiState.update { current ->
                    val updated = current.rooms.map { room ->
                        if (room.id == action.roomId) {
                            room.copy(isPoweredOn = !room.isPoweredOn)
                        } else {
                            room
                        }
                    }
                    val target = current.rooms.find { it.id == action.roomId }
                    val state = if (target?.isPoweredOn == true) "Offline" else "Online"
                    current.copy(
                        rooms = updated,
                        userFeedback = "${target?.name ?: "Room"} Master Power $state"
                    )
                }
            }

            is RoomsUiAction.SetSliderLevel -> {
                _uiState.update { current ->
                    val updated = current.rooms.map { room ->
                        if (room.id == action.roomId) {
                            room.copy(sliderLevel = action.level.coerceIn(0f, 1f))
                        } else {
                            room
                        }
                    }
                    current.copy(rooms = updated)
                }
            }

            is RoomsUiAction.ConfigureHabitats -> {
                _uiState.update { current ->
                    current.copy(
                        userFeedback = "Zone Mesh Configurator: Opening Habitat Topology Matrix"
                    )
                }
            }

            is RoomsUiAction.PairNewNode -> {
                _uiState.update { current ->
                    current.copy(
                        userFeedback = "Node Pairing Radar: Scanning Zigbee / Thread Mesh Nodes..."
                    )
                }
            }

            is RoomsUiAction.ClearFeedback -> {
                _uiState.update { it.copy(userFeedback = null) }
            }
        }
    }
}
