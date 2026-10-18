package com.example.project312.data

import com.example.project312.model.HabitatTelemetry
import com.example.project312.model.RoomCategory
import com.example.project312.model.RoomItem
import com.example.project312.model.RoutineActionAccent
import com.example.project312.model.RoutineActionIcon
import com.example.project312.model.RoutineActionItem
import com.example.project312.model.RoutineCategoryIcon
import com.example.project312.model.RoutineTriggerIcon
import com.example.project312.model.ScheduledSequenceItem
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val roomsCollection = firestore.collection("rooms")
    private val routinesCollection = firestore.collection("routines")
    private val devicesCollection = firestore.collection("devices")
    private val instantActionsCollection = firestore.collection("instant_actions")
    private val appliancesCollection = firestore.collection("appliances")
    private val telemetryDoc = firestore.collection("habitats").document("nexus_core")
    private val settingsDoc = firestore.collection("settings").document("nexus_settings")

    /**
     * Seeds initial default data to Firestore if collections are empty.
     */
    suspend fun seedInitialDataIfEmpty() {
        try {
            val defaultRooms = listOf(
                hashMapOf(
                    "id" to "living_room",
                    "name" to "Living Room",
                    "subtitle" to "6 Devices Online",
                    "category" to "LIVING_ROOM",
                    "isPoweredOn" to true,
                    "tempC" to "22°C",
                    "aqi" to "98 AQI",
                    "lux" to "450 lx",
                    "noiseDb" to "24 dB (Silent)",
                    "deepRemTemp" to "20.0°C",
                    "sliderLevel" to 0.74,
                    "spatialAudioLevel" to 0.80,
                    "gridLoadKw" to "3.4 kW",
                    "badgeText" to null,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "master_suite",
                    "name" to "Master Cyber Suite",
                    "subtitle" to "Sleep Sanctuary Active",
                    "category" to "MASTER_SUITE",
                    "isPoweredOn" to true,
                    "tempC" to "20°C",
                    "aqi" to "95 AQI",
                    "lux" to "300 lx",
                    "noiseDb" to "24 dB (Silent)",
                    "deepRemTemp" to "20.0°C",
                    "sliderLevel" to 0.60,
                    "spatialAudioLevel" to 0.70,
                    "gridLoadKw" to "1.8 kW",
                    "badgeText" to null,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "holodeck_studio",
                    "name" to "Holodeck Studio",
                    "subtitle" to "High Precision Simulator Zone",
                    "category" to "HOLODECK_STUDIO",
                    "isPoweredOn" to true,
                    "tempC" to "21°C",
                    "aqi" to "99 AQI",
                    "lux" to "500 lx",
                    "noiseDb" to "30 dB",
                    "deepRemTemp" to "21.0°C",
                    "sliderLevel" to 0.80,
                    "spatialAudioLevel" to 0.80,
                    "gridLoadKw" to "3.4 kW",
                    "badgeText" to "OVERCLOCK",
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "cyber_kitchen",
                    "name" to "Cyber Kitchen",
                    "subtitle" to "Safety Grid 100% Secure",
                    "category" to "CYBER_KITCHEN",
                    "isPoweredOn" to true,
                    "tempC" to "-18°C / 4°C",
                    "aqi" to "98 AQI",
                    "lux" to "420 lx",
                    "noiseDb" to "22 dB",
                    "deepRemTemp" to "20.0°C",
                    "sliderLevel" to 0.70,
                    "spatialAudioLevel" to 0.50,
                    "gridLoadKw" to "2.1 kW",
                    "badgeText" to "Auto-Guarded",
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )

            defaultRooms.forEach { roomMap ->
                val id = roomMap["id"] as String
                roomsCollection.document(id).set(roomMap, com.google.firebase.firestore.SetOptions.merge())
            }

            val defaultRoutines = listOf(
                hashMapOf(
                    "id" to "sunrise_awakening",
                    "title" to "Sunrise Awakening",
                    "triggerText" to "06:30 AM Daily",
                    "triggerIcon" to "ALARM",
                    "categoryIcon" to "SUNRISE",
                    "categoryAccent" to "EMERALD",
                    "isEnabled" to true,
                    "actions" to listOf(
                        mapOf("label" to "Gradual 10% → 100%", "iconType" to "LIGHT_GRADUAL", "accent" to "EMERALD"),
                        mapOf("label" to "Blinds Open 75%", "iconType" to "BLINDS", "accent" to "CYAN"),
                        mapOf("label" to "Morning Broadcast", "iconType" to "BROADCAST", "accent" to "PURPLE"),
                        mapOf("label" to "Espresso Brew Start", "iconType" to "COFFEE", "accent" to "EMERALD")
                    ),
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "focus_matrix",
                    "title" to "Focus Matrix",
                    "triggerText" to "Trigger: NFC Desk Tag",
                    "triggerIcon" to "NFC",
                    "categoryIcon" to "MATRIX",
                    "categoryAccent" to "CYAN",
                    "isEnabled" to true,
                    "actions" to listOf(
                        mapOf("label" to "Blue Light 450nm", "iconType" to "BLUE_LIGHT", "accent" to "CYAN"),
                        mapOf("label" to "ANC Level 100%", "iconType" to "ANC", "accent" to "PURPLE"),
                        mapOf("label" to "DND Shield On", "iconType" to "DND", "accent" to "CRIMSON"),
                        mapOf("label" to "Airflow 22°C Silent", "iconType" to "AIRFLOW", "accent" to "EMERALD")
                    ),
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "sunset_transition",
                    "title" to "Sunset Transition",
                    "triggerText" to "Astronomical Twilight (18:45)",
                    "triggerIcon" to "TWILIGHT",
                    "categoryIcon" to "SUNSET",
                    "categoryAccent" to "PURPLE",
                    "isEnabled" to true,
                    "actions" to listOf(
                        mapOf("label" to "Warm Glow 2200K", "iconType" to "AMBER_LIGHT", "accent" to "PURPLE"),
                        mapOf("label" to "Perimeter Shield", "iconType" to "PERIMETER", "accent" to "EMERALD"),
                        mapOf("label" to "Eco Temp 21°C", "iconType" to "THERMOSTAT", "accent" to "CYAN"),
                        mapOf("label" to "Privacy Shades Closed", "iconType" to "SHADES", "accent" to "PURPLE")
                    ),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )

            defaultRoutines.forEach { routineMap ->
                val id = routineMap["id"] as String
                routinesCollection.document(id).set(routineMap, com.google.firebase.firestore.SetOptions.merge())
            }

            val initial = HabitatTelemetry()
            telemetryDoc.set(
                hashMapOf(
                    "habitatName" to initial.habitatName,
                    "sectorName" to initial.sectorName,
                    "overallStatus" to initial.overallStatus,
                    "overallTemp" to initial.overallTemp,
                    "ambientTemp" to initial.ambientTemp,
                    "humidityPercent" to initial.humidityPercent,
                    "aqi" to initial.aqi,
                    "aqiStatus" to initial.aqiStatus,
                    "activeSensorsCount" to initial.activeSensorsCount,
                    "scrubbersRunning" to initial.scrubbersRunning,
                    "airPurityPercent" to initial.airPurityPercent,
                    "powerDemandKw" to initial.powerDemandKw,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )

            val defaultDevices = listOf(
                hashMapOf(
                    "id" to "cyberlight_pro",
                    "name" to "CyberLight Pro",
                    "subtitle" to "Living • Cyan Tint",
                    "badgeText" to "75%",
                    "level" to 0.75,
                    "deviceType" to "LIGHT",
                    "accent" to "CYAN",
                    "isPlayingAudio" to false,
                    "isPoweredOn" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "climate_pod",
                    "name" to "Climate Pod",
                    "subtitle" to "Target 21.5°C",
                    "badgeText" to "Cooling",
                    "level" to 0.65,
                    "deviceType" to "CLIMATE",
                    "accent" to "CYAN_PURPLE_GRADIENT",
                    "isPlayingAudio" to false,
                    "isPoweredOn" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "soundwave_ultra",
                    "name" to "SoundWave Ultra",
                    "subtitle" to "Cyberpunk Lofi",
                    "badgeText" to "Lofi",
                    "level" to 0.60,
                    "deviceType" to "AUDIO",
                    "accent" to "PURPLE",
                    "isPlayingAudio" to true,
                    "isPoweredOn" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "smart_blinds",
                    "name" to "Smart Blinds",
                    "subtitle" to "East Facade Open",
                    "badgeText" to "80%",
                    "level" to 0.80,
                    "deviceType" to "BLINDS",
                    "accent" to "EMERALD",
                    "isPlayingAudio" to false,
                    "isPoweredOn" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            defaultDevices.forEach { devMap ->
                val id = devMap["id"] as String
                devicesCollection.document(id).set(devMap, com.google.firebase.firestore.SetOptions.merge())
            }

            val defaultActions = listOf(
                hashMapOf(
                    "id" to "ambient_glow",
                    "title" to "Ambient Glow",
                    "subtitle" to "Atmosphere 4000K",
                    "icon" to "LIGHT",
                    "accent" to "CYAN",
                    "isEnabled" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "climate_units",
                    "title" to "All Climate Units",
                    "subtitle" to "Preset 21.0°C",
                    "icon" to "CLIMATE",
                    "accent" to "CYAN",
                    "isEnabled" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "lock_doors",
                    "title" to "Lock All Doors",
                    "subtitle" to "Biometric Perimeter",
                    "icon" to "LOCK",
                    "accent" to "PURPLE",
                    "isEnabled" to true,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "turbo_purify",
                    "title" to "Turbo Purify",
                    "subtitle" to "Standby Mode",
                    "icon" to "FAN",
                    "accent" to "EMERALD",
                    "isEnabled" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            defaultActions.forEach { actMap ->
                val id = actMap["id"] as String
                instantActionsCollection.document(id).set(actMap, com.google.firebase.firestore.SetOptions.merge())
            }

            val defaultAppliances = listOf(
                hashMapOf(
                    "id" to "climate_control",
                    "name" to "Climate Control System",
                    "zoneDescription" to "HVAC Zone 01 & 02",
                    "powerKw" to 1.19,
                    "percentage" to 42,
                    "accent" to "CYAN",
                    "ecoTip" to "Eco Tip: Shifting +1°C saves 0.18 kW/hr",
                    "isEcoTipApplied" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "water_heater",
                    "name" to "Quantum Water Heater",
                    "zoneDescription" to "Thermal storage: 91%",
                    "powerKw" to 0.68,
                    "percentage" to 24,
                    "accent" to "PURPLE",
                    "ecoTip" to null,
                    "isEcoTipApplied" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "ambient_lighting",
                    "name" to "Cyber Ambient Lighting",
                    "zoneDescription" to "Circadian sequence enabled",
                    "powerKw" to 0.51,
                    "percentage" to 18,
                    "accent" to "CYAN_DIM",
                    "ecoTip" to null,
                    "isEcoTipApplied" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                hashMapOf(
                    "id" to "studio_workstations",
                    "name" to "Studio Workstations",
                    "zoneDescription" to "3 Units • Sleep ready",
                    "powerKw" to 0.46,
                    "percentage" to 16,
                    "accent" to "EMERALD",
                    "ecoTip" to null,
                    "isEcoTipApplied" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            )
            defaultAppliances.forEach { appMap ->
                val id = appMap["id"] as String
                appliancesCollection.document(id).set(appMap, com.google.firebase.firestore.SetOptions.merge())
            }

            settingsDoc.set(
                hashMapOf(
                    "threadMeshActive" to true,
                    "localAiEdgeInference" to true,
                    "matterBridgeSync" to true,
                    "zigbeeChannel" to 25,
                    "cloudTelemetrySync" to false,
                    "autonomousEcoTuning" to true,
                    "geofenceAutoArm" to true,
                    "millimeterPresence" to true,
                    "tempUnit" to "CELSIUS",
                    "aqiAlertThreshold" to 100,
                    "circadianSpectrumLighting" to true,
                    "ultraGlassShaders" to true,
                    "tacticalHaptics" to true,
                    "selectedAccent" to "ELECTRIC_CYAN",
                    "ambientBackdropGlows" to true,
                    "isEmergencyGridLocked" to false,
                    "updatedAt" to FieldValue.serverTimestamp()
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )
        } catch (e: Exception) {
            android.util.Log.e("FirebaseFirestoreRepo", "Error seeding Firestore data: ${e.message}", e)
        }
    }

    /**
     * Real-time Flow of Rooms from Firestore
     */
    fun observeRooms(): Flow<List<RoomItem>> = callbackFlow {
        val listener = roomsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.isEmpty) {
                val items = snapshot.documents.mapNotNull { doc ->
                    try {
                        val categoryStr = doc.getString("category") ?: RoomCategory.LIVING_ROOM.name
                        val category = try {
                            RoomCategory.valueOf(categoryStr)
                        } catch (e: Exception) {
                            RoomCategory.LIVING_ROOM
                        }

                        RoomItem(
                            id = doc.id,
                            name = doc.getString("name") ?: "Zone",
                            subtitle = doc.getString("subtitle") ?: "",
                            category = category,
                            isPoweredOn = doc.getBoolean("isPoweredOn") ?: true,
                            tempC = doc.getString("tempC") ?: "22°C",
                            aqi = doc.getString("aqi") ?: "98 AQI",
                            lux = doc.getString("lux") ?: "450 lx",
                            noiseDb = doc.getString("noiseDb") ?: "24 dB (Silent)",
                            deepRemTemp = doc.getString("deepRemTemp") ?: "20.0°C",
                            sliderLevel = (doc.getDouble("sliderLevel") ?: 0.74).toFloat(),
                            spatialAudioLevel = (doc.getDouble("spatialAudioLevel") ?: 0.80).toFloat(),
                            gridLoadKw = doc.getString("gridLoadKw") ?: "3.4 kW",
                            badgeText = doc.getString("badgeText")
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateRoomPower(roomId: String, isPoweredOn: Boolean) {
        try {
            roomsCollection.document(roomId).update(
                mapOf(
                    "isPoweredOn" to isPoweredOn,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    suspend fun updateRoomSlider(roomId: String, level: Float) {
        try {
            roomsCollection.document(roomId).update(
                mapOf(
                    "sliderLevel" to level.coerceIn(0f, 1f).toDouble(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    /**
     * Real-time Flow of Routines from Firestore
     */
    fun observeRoutines(): Flow<List<ScheduledSequenceItem>> = callbackFlow {
        val listener = routinesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.isEmpty) {
                val items = snapshot.documents.mapNotNull { doc ->
                    try {
                        val triggerIconStr = doc.getString("triggerIcon") ?: RoutineTriggerIcon.ALARM.name
                        val triggerIcon = try { RoutineTriggerIcon.valueOf(triggerIconStr) } catch (e: Exception) { RoutineTriggerIcon.ALARM }

                        val categoryIconStr = doc.getString("categoryIcon") ?: RoutineCategoryIcon.SUNRISE.name
                        val categoryIcon = try { RoutineCategoryIcon.valueOf(categoryIconStr) } catch (e: Exception) { RoutineCategoryIcon.SUNRISE }

                        val categoryAccentStr = doc.getString("categoryAccent") ?: RoutineActionAccent.EMERALD.name
                        val categoryAccent = try { RoutineActionAccent.valueOf(categoryAccentStr) } catch (e: Exception) { RoutineActionAccent.EMERALD }

                        @Suppress("UNCHECKED_CAST")
                        val rawActions = doc.get("actions") as? List<Map<String, Any>> ?: emptyList()
                        val actions = rawActions.map { aMap ->
                            val iconStr = aMap["iconType"] as? String ?: RoutineActionIcon.LIGHT_GRADUAL.name
                            val accentStr = aMap["accent"] as? String ?: RoutineActionAccent.EMERALD.name
                            RoutineActionItem(
                                label = aMap["label"] as? String ?: "",
                                iconType = try { RoutineActionIcon.valueOf(iconStr) } catch (e: Exception) { RoutineActionIcon.LIGHT_GRADUAL },
                                accent = try { RoutineActionAccent.valueOf(accentStr) } catch (e: Exception) { RoutineActionAccent.EMERALD }
                            )
                        }

                        ScheduledSequenceItem(
                            id = doc.id,
                            title = doc.getString("title") ?: "Routine",
                            triggerText = doc.getString("triggerText") ?: "",
                            triggerIcon = triggerIcon,
                            categoryIcon = categoryIcon,
                            categoryAccent = categoryAccent,
                            isEnabled = doc.getBoolean("isEnabled") ?: true,
                            actions = actions
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateRoutineEnabled(routineId: String, isEnabled: Boolean) {
        try {
            routinesCollection.document(routineId).update(
                mapOf(
                    "isEnabled" to isEnabled,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    /**
     * Real-time Flow of Habitat Telemetry
     */
    fun observeTelemetry(): Flow<HabitatTelemetry> = callbackFlow {
        val listener = telemetryDoc.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val telemetry = HabitatTelemetry(
                    habitatName = snapshot.getString("habitatName") ?: "Nexus Habitat",
                    sectorName = snapshot.getString("sectorName") ?: "Living Sector Alpha",
                    overallStatus = snapshot.getString("overallStatus") ?: "Optimal",
                    overallTemp = snapshot.getDouble("overallTemp") ?: 24.0,
                    ambientTemp = snapshot.getDouble("ambientTemp") ?: 22.5,
                    humidityPercent = snapshot.getLong("humidityPercent")?.toInt() ?: 48,
                    aqi = snapshot.getLong("aqi")?.toInt() ?: 14,
                    aqiStatus = snapshot.getString("aqiStatus") ?: "Optimal",
                    activeSensorsCount = snapshot.getLong("activeSensorsCount")?.toInt() ?: 18,
                    scrubbersRunning = snapshot.getBoolean("scrubbersRunning") ?: true,
                    airPurityPercent = snapshot.getDouble("airPurityPercent") ?: 99.4,
                    powerDemandKw = snapshot.getDouble("powerDemandKw") ?: 1.2
                )
                trySend(telemetry)
            }
        }
        awaitClose { listener.remove() }
    }

    /**
     * Real-time Flow of Smart Devices
     */
    fun observeDevices(): Flow<List<com.example.project312.model.SmartDeviceItem>> = callbackFlow {
        val listener = devicesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null && !snapshot.isEmpty) {
                val items = snapshot.documents.mapNotNull { doc ->
                    try {
                        val devTypeStr = doc.getString("deviceType") ?: com.example.project312.model.DeviceType.LIGHT.name
                        val devType = try { com.example.project312.model.DeviceType.valueOf(devTypeStr) } catch (e: Exception) { com.example.project312.model.DeviceType.LIGHT }

                        val accentStr = doc.getString("accent") ?: com.example.project312.model.DeviceAccent.CYAN.name
                        val accent = try { com.example.project312.model.DeviceAccent.valueOf(accentStr) } catch (e: Exception) { com.example.project312.model.DeviceAccent.CYAN }

                        com.example.project312.model.SmartDeviceItem(
                            id = doc.id,
                            name = doc.getString("name") ?: "Device",
                            subtitle = doc.getString("subtitle") ?: "",
                            badgeText = doc.getString("badgeText") ?: "",
                            level = (doc.getDouble("level") ?: 0.5).toFloat(),
                            deviceType = devType,
                            accent = accent,
                            isPlayingAudio = doc.getBoolean("isPlayingAudio") ?: false,
                            isPoweredOn = doc.getBoolean("isPoweredOn") ?: true
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateDevicePower(deviceId: String, isPoweredOn: Boolean) {
        try {
            devicesCollection.document(deviceId).update(
                mapOf(
                    "isPoweredOn" to isPoweredOn,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    suspend fun updateDeviceLevel(deviceId: String, level: Float, badgeText: String) {
        try {
            devicesCollection.document(deviceId).update(
                mapOf(
                    "level" to level.coerceIn(0f, 1f).toDouble(),
                    "badgeText" to badgeText,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    /**
     * Real-time Flow of Instant Actions
     */
    fun observeInstantActions(): Flow<List<com.example.project312.model.QuickActionItem>> = callbackFlow {
        val listener = instantActionsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null && !snapshot.isEmpty) {
                val items = snapshot.documents.mapNotNull { doc ->
                    try {
                        val iconStr = doc.getString("icon") ?: com.example.project312.model.QuickActionIcon.LIGHT.name
                        val icon = try { com.example.project312.model.QuickActionIcon.valueOf(iconStr) } catch (e: Exception) { com.example.project312.model.QuickActionIcon.LIGHT }

                        val accentStr = doc.getString("accent") ?: com.example.project312.model.QuickActionAccent.CYAN.name
                        val accent = try { com.example.project312.model.QuickActionAccent.valueOf(accentStr) } catch (e: Exception) { com.example.project312.model.QuickActionAccent.CYAN }

                        com.example.project312.model.QuickActionItem(
                            id = doc.id,
                            title = doc.getString("title") ?: "Action",
                            subtitle = doc.getString("subtitle") ?: "",
                            icon = icon,
                            accent = accent,
                            isEnabled = doc.getBoolean("isEnabled") ?: false
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateInstantAction(actionId: String, isEnabled: Boolean) {
        try {
            instantActionsCollection.document(actionId).update(
                mapOf(
                    "isEnabled" to isEnabled,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    /**
     * Real-time Flow of Energy Appliances
     */
    fun observeAppliances(): Flow<List<com.example.project312.model.ApplianceDrainItem>> = callbackFlow {
        val listener = appliancesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null && !snapshot.isEmpty) {
                val items = snapshot.documents.mapNotNull { doc ->
                    try {
                        val accentStr = doc.getString("accent") ?: com.example.project312.model.ApplianceAccent.CYAN.name
                        val accent = try { com.example.project312.model.ApplianceAccent.valueOf(accentStr) } catch (e: Exception) { com.example.project312.model.ApplianceAccent.CYAN }

                        com.example.project312.model.ApplianceDrainItem(
                            id = doc.id,
                            name = doc.getString("name") ?: "Appliance",
                            zoneDescription = doc.getString("zoneDescription") ?: "",
                            powerKw = doc.getDouble("powerKw") ?: 0.5,
                            percentage = doc.getLong("percentage")?.toInt() ?: 20,
                            accent = accent,
                            ecoTip = doc.getString("ecoTip"),
                            isEcoTipApplied = doc.getBoolean("isEcoTipApplied") ?: false
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(items)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateApplianceEcoTip(applianceId: String, isEcoTipApplied: Boolean) {
        try {
            appliancesCollection.document(applianceId).update(
                mapOf(
                    "isEcoTipApplied" to isEcoTipApplied,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }

    /**
     * Real-time Flow of Settings
     */
    fun observeSettings(): Flow<Map<String, Any?>> = callbackFlow {
        val listener = settingsDoc.addSnapshotListener { snapshot, error ->
            if (error != null) return@addSnapshotListener
            if (snapshot != null && snapshot.exists()) {
                trySend(snapshot.data ?: emptyMap())
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun updateSettingsField(field: String, value: Any?) {
        try {
            settingsDoc.update(
                mapOf(
                    field to value,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // Offline fallback
        }
    }
}
