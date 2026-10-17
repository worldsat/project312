package com.example.project312.model

import androidx.compose.runtime.Immutable

@Immutable
data class PowerFluxTelemetry(
    val totalDemandKw: Double = 2.84,
    val selfSustainedPercent: Int = 58,
    val solarInflowKw: Double = 1.65,
    val activePanelsCount: Int = 12,
    val gridPullKw: Double = 1.19,
    val gridTierRate: String = "Off-peak tier: $0.11",
    val lastUpdatedText: String = "Update: Just now",
    val demandArcFraction: Float = 0.71f, // progress around the circular gauge
    val ghostArcFraction: Float = 0.44f
)

enum class TimelinePeriod(val label: String) {
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year")
}

@Immutable
data class ConsumptionDynamicsData(
    val selectedPeriod: TimelinePeriod = TimelinePeriod.DAY,
    val offPeakWindow: String = "Off-Peak Window: 23:00 - 06:00",
    val costDelta: String = "-22% Cost Delta",
    val peakLabel: String = "Peak: 3.4 kW (14:30)",
    val dailyRunRate: String = "-14.2% avg",
    val cycleSavings: String = "$48.70 saved",
    val timeLabels: List<String> = listOf("00:00", "06:00", "12:00", "18:00", "23:59")
)

enum class ApplianceAccent {
    CYAN,
    PURPLE,
    CYAN_DIM,
    EMERALD
}

@Immutable
data class ApplianceDrainItem(
    val id: String,
    val name: String,
    val zoneDescription: String,
    val powerKw: Double,
    val percentage: Int,
    val accent: ApplianceAccent,
    val ecoTip: String? = null,
    val isEcoTipApplied: Boolean = false
)

@Immutable
data class NexusIntelligenceData(
    val ecoScore: Int = 94,
    val maxScore: Int = 100,
    val habitatStatus: String = "Superior Habitat Status",
    val comparisonText: String = "Outperforming 92% of local microgrids",
    val suggestionTitle: String = "Autonomous Load Balancing Ready",
    val suggestionDescription: String = "Diverting non-essential water heater cycles to match predicted solar surge at 13:00.",
    val isOptimizationApplied: Boolean = false,
    val optimizationSavingsKw: Double = 0.4
)

@Immutable
data class EnergyUiState(
    val powerFlux: PowerFluxTelemetry = PowerFluxTelemetry(),
    val consumptionDynamics: ConsumptionDynamicsData = ConsumptionDynamicsData(),
    val appliances: List<ApplianceDrainItem> = emptyList(),
    val intelligence: NexusIntelligenceData = NexusIntelligenceData(),
    val userFeedback: String? = null
)

sealed interface EnergyUiAction {
    data class SelectPeriod(val period: TimelinePeriod) : EnergyUiAction
    data class ApplyApplianceEcoTip(val applianceId: String) : EnergyUiAction
    data object ApplyAutoPowerOptimization : EnergyUiAction
    data object ClearFeedback : EnergyUiAction
}
