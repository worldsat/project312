package com.example.project312.model

import androidx.compose.runtime.Immutable

enum class RoomFilter(val label: String, val iconType: RoomFilterIcon) {
    ALL("All Zones (5)", RoomFilterIcon.DOT),
    LIVING_ROOM("Living Room", RoomFilterIcon.WEEKEND),
    CYBER_KITCHEN("Cyber Kitchen", RoomFilterIcon.COUNTERTOPS),
    MASTER_SUITE("Master Suite", RoomFilterIcon.BED),
    HOLODECK_STUDIO("Holodeck / Studio", RoomFilterIcon.VIEW_IN_AR),
    PATIO("Patio", RoomFilterIcon.DECK)
}

enum class RoomFilterIcon {
    DOT,
    WEEKEND,
    COUNTERTOPS,
    BED,
    VIEW_IN_AR,
    DECK
}

enum class RoomCategory {
    LIVING_ROOM,
    MASTER_SUITE,
    HOLODECK_STUDIO,
    CYBER_KITCHEN
}

@Immutable
data class RoomItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val category: RoomCategory,
    val isPoweredOn: Boolean = true,
    val tempC: String = "22°C",
    val aqi: String = "98 AQI",
    val lux: String = "450 lx",
    val noiseDb: String = "24 dB (Silent)",
    val deepRemTemp: String = "20.0°C",
    val sliderLevel: Float = 0.74f,
    val spatialAudioLevel: Float = 0.80f,
    val gridLoadKw: String = "3.4 kW",
    val badgeText: String? = null
)

@Immutable
data class RoomsUiState(
    val selectedFilter: RoomFilter = RoomFilter.ALL,
    val rooms: List<RoomItem> = emptyList(),
    val totalZonesCount: Int = 5,
    val userFeedback: String? = null
)

sealed interface RoomsUiAction {
    data class SelectFilter(val filter: RoomFilter) : RoomsUiAction
    data class ToggleRoomPower(val roomId: String) : RoomsUiAction
    data class SetSliderLevel(val roomId: String, val level: Float) : RoomsUiAction
    data object ConfigureHabitats : RoomsUiAction
    data object PairNewNode : RoomsUiAction
    data object ClearFeedback : RoomsUiAction
}
