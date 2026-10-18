package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.FirebaseFirestoreRepository
import com.example.project312.model.RoomCategory
import com.example.project312.model.RoomFilter
import com.example.project312.model.RoomsUiAction
import com.example.project312.model.RoomsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomsViewModel(
    private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private var allCloudRooms = listOf<com.example.project312.model.RoomItem>()
    private val _uiState = MutableStateFlow(RoomsUiState())
    val uiState: StateFlow<RoomsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreRepository.seedInitialDataIfEmpty()
        }
        viewModelScope.launch {
            firestoreRepository.observeRooms().collect { cloudRooms ->
                allCloudRooms = cloudRooms
                applyFilter(_uiState.value.selectedFilter)
            }
        }
    }

    private fun applyFilter(filter: RoomFilter) {
        val filtered = when (filter) {
            RoomFilter.ALL -> allCloudRooms
            RoomFilter.LIVING_ROOM -> allCloudRooms.filter { it.category == RoomCategory.LIVING_ROOM }
            RoomFilter.CYBER_KITCHEN -> allCloudRooms.filter { it.category == RoomCategory.CYBER_KITCHEN }
            RoomFilter.MASTER_SUITE -> allCloudRooms.filter { it.category == RoomCategory.MASTER_SUITE }
            RoomFilter.HOLODECK_STUDIO -> allCloudRooms.filter { it.category == RoomCategory.HOLODECK_STUDIO }
            RoomFilter.PATIO -> emptyList()
        }
        _uiState.update { it.copy(selectedFilter = filter, rooms = filtered, totalZonesCount = allCloudRooms.size) }
    }

    fun onAction(action: RoomsUiAction) {
        when (action) {
            is RoomsUiAction.SelectFilter -> {
                applyFilter(action.filter)
                _uiState.update { it.copy(userFeedback = "Viewing ${action.filter.label}") }
            }

            is RoomsUiAction.ToggleRoomPower -> {
                val target = _uiState.value.rooms.find { it.id == action.roomId }
                val nextState = if (target?.isPoweredOn == true) false else true
                _uiState.update { current ->
                    val updated = current.rooms.map { room ->
                        if (room.id == action.roomId) {
                            room.copy(isPoweredOn = nextState)
                        } else {
                            room
                        }
                    }
                    val state = if (nextState) "Online" else "Offline"
                    current.copy(
                        rooms = updated,
                        userFeedback = "${target?.name ?: "Room"} Master Power $state"
                    )
                }
                viewModelScope.launch {
                    firestoreRepository.updateRoomPower(action.roomId, nextState)
                }
            }

            is RoomsUiAction.SetSliderLevel -> {
                val clamped = action.level.coerceIn(0f, 1f)
                _uiState.update { current ->
                    val updated = current.rooms.map { room ->
                        if (room.id == action.roomId) {
                            room.copy(sliderLevel = clamped)
                        } else {
                            room
                        }
                    }
                    current.copy(rooms = updated)
                }
                viewModelScope.launch {
                    firestoreRepository.updateRoomSlider(action.roomId, clamped)
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
