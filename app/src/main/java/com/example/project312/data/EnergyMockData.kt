package com.example.project312.data

import com.example.project312.model.ApplianceAccent
import com.example.project312.model.ApplianceDrainItem
import com.example.project312.model.ConsumptionDynamicsData
import com.example.project312.model.EnergyUiState
import com.example.project312.model.NexusIntelligenceData
import com.example.project312.model.PowerFluxTelemetry
import com.example.project312.model.TimelinePeriod

object EnergyMockData {

    val initialPowerFlux = PowerFluxTelemetry(
        totalDemandKw = 2.84,
        selfSustainedPercent = 58,
        solarInflowKw = 1.65,
        activePanelsCount = 12,
        gridPullKw = 1.19,
        gridTierRate = "Off-peak tier: $0.11",
        lastUpdatedText = "Update: Just now",
        demandArcFraction = 0.71f,
        ghostArcFraction = 0.44f
    )

    val initialConsumptionDynamics = ConsumptionDynamicsData(
        selectedPeriod = TimelinePeriod.DAY,
        offPeakWindow = "Off-Peak Window: 23:00 - 06:00",
        costDelta = "-22% Cost Delta",
        peakLabel = "Peak: 3.4 kW (14:30)",
        dailyRunRate = "-14.2% avg",
        cycleSavings = "$48.70 saved",
        timeLabels = listOf("00:00", "06:00", "12:00", "18:00", "23:59")
    )

    val initialAppliances = listOf(
        ApplianceDrainItem(
            id = "climate_control",
            name = "Climate Control System",
            zoneDescription = "HVAC Zone 01 & 02",
            powerKw = 1.19,
            percentage = 42,
            accent = ApplianceAccent.CYAN,
            ecoTip = "Eco Tip: Shifting +1°C saves 0.18 kW/hr",
            isEcoTipApplied = false
        ),
        ApplianceDrainItem(
            id = "water_heater",
            name = "Quantum Water Heater",
            zoneDescription = "Thermal storage: 91%",
            powerKw = 0.68,
            percentage = 24,
            accent = ApplianceAccent.PURPLE,
            ecoTip = null,
            isEcoTipApplied = false
        ),
        ApplianceDrainItem(
            id = "ambient_lighting",
            name = "Cyber Ambient Lighting",
            zoneDescription = "Circadian sequence enabled",
            powerKw = 0.51,
            percentage = 18,
            accent = ApplianceAccent.CYAN_DIM,
            ecoTip = null,
            isEcoTipApplied = false
        ),
        ApplianceDrainItem(
            id = "studio_workstations",
            name = "Studio Workstations",
            zoneDescription = "3 Units • Sleep ready",
            powerKw = 0.46,
            percentage = 16,
            accent = ApplianceAccent.EMERALD,
            ecoTip = null,
            isEcoTipApplied = false
        )
    )

    val initialIntelligence = NexusIntelligenceData(
        ecoScore = 94,
        maxScore = 100,
        habitatStatus = "Superior Habitat Status",
        comparisonText = "Outperforming 92% of local microgrids",
        suggestionTitle = "Autonomous Load Balancing Ready",
        suggestionDescription = "Diverting non-essential water heater cycles to match predicted solar surge at 13:00.",
        isOptimizationApplied = false,
        optimizationSavingsKw = 0.4
    )

    fun createInitialState(): EnergyUiState {
        return EnergyUiState(
            powerFlux = initialPowerFlux,
            consumptionDynamics = initialConsumptionDynamics,
            appliances = initialAppliances,
            intelligence = initialIntelligence,
            userFeedback = null
        )
    }
}
