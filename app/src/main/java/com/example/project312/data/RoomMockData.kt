package com.example.project312.data

import com.example.project312.model.RoomCategory
import com.example.project312.model.RoomFilter
import com.example.project312.model.RoomItem
import com.example.project312.model.RoomsUiState

object RoomMockData {

    val initialRooms = listOf(
        RoomItem(
            id = "living_room",
            name = "Living Room",
            subtitle = "6 Devices Online",
            category = RoomCategory.LIVING_ROOM,
            isPoweredOn = true,
            tempC = "22°C",
            aqi = "98 AQI",
            lux = "450 lx",
            sliderLevel = 0.74f,
            badgeText = null
        ),
        RoomItem(
            id = "master_suite",
            name = "Master Cyber Suite",
            subtitle = "Sleep Sanctuary Active",
            category = RoomCategory.MASTER_SUITE,
            isPoweredOn = true,
            noiseDb = "24 dB (Silent)",
            deepRemTemp = "20.0°C",
            sliderLevel = 0.60f,
            badgeText = null
        ),
        RoomItem(
            id = "holodeck_studio",
            name = "Holodeck Studio",
            subtitle = "High Precision Simulator Zone",
            category = RoomCategory.HOLODECK_STUDIO,
            isPoweredOn = true,
            spatialAudioLevel = 0.80f,
            gridLoadKw = "3.4 kW",
            badgeText = "OVERCLOCK"
        ),
        RoomItem(
            id = "cyber_kitchen",
            name = "Cyber Kitchen",
            subtitle = "Safety Grid 100% Secure",
            category = RoomCategory.CYBER_KITCHEN,
            isPoweredOn = true,
            tempC = "-18°C / 4°C",
            badgeText = "Auto-Guarded"
        )
    )

    fun createInitialState(): RoomsUiState {
        return RoomsUiState(
            selectedFilter = RoomFilter.ALL,
            rooms = initialRooms,
            totalZonesCount = 5,
            userFeedback = null
        )
    }
}
