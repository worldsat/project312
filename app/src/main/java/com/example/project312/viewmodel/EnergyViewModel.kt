package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.FirebaseFirestoreRepository
import com.example.project312.model.ApplianceAccent
import com.example.project312.model.ApplianceDrainItem
import com.example.project312.model.EnergyUiAction
import com.example.project312.model.EnergyUiState
import com.example.project312.model.TimelinePeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnergyViewModel(
    private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private val initialAppliances = listOf(
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

    private val _uiState = MutableStateFlow(EnergyUiState(appliances = initialAppliances))
    val uiState: StateFlow<EnergyUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreRepository.observeAppliances().collect { cloudAppliances ->
                if (cloudAppliances.isNotEmpty()) {
                    _uiState.update { it.copy(appliances = cloudAppliances) }
                }
            }
        }
    }

    fun onAction(action: EnergyUiAction) {
        when (action) {
            is EnergyUiAction.SelectPeriod -> {
                _uiState.update { current ->
                    val updatedDynamics = current.consumptionDynamics.copy(
                        selectedPeriod = action.period,
                        costDelta = when (action.period) {
                            TimelinePeriod.DAY -> "-22% Cost Delta"
                            TimelinePeriod.WEEK -> "-18.5% Cost Delta"
                            TimelinePeriod.MONTH -> "-25.2% Cost Delta"
                            TimelinePeriod.YEAR -> "-31.0% Cost Delta"
                        },
                        dailyRunRate = when (action.period) {
                            TimelinePeriod.DAY -> "-14.2% avg"
                            TimelinePeriod.WEEK -> "-12.8% avg"
                            TimelinePeriod.MONTH -> "-16.1% avg"
                            TimelinePeriod.YEAR -> "-19.4% avg"
                        }
                    )
                    current.copy(
                        consumptionDynamics = updatedDynamics,
                        userFeedback = "Timeline shifted to ${action.period.label} view"
                    )
                }
            }

            is EnergyUiAction.ApplyApplianceEcoTip -> {
                _uiState.update { current ->
                    val updatedAppliances = current.appliances.map { item ->
                        if (item.id == action.applianceId) {
                            item.copy(isEcoTipApplied = true)
                        } else {
                            item
                        }
                    }
                    val targetAppliance = current.appliances.find { it.id == action.applianceId }
                    current.copy(
                        appliances = updatedAppliances,
                        userFeedback = "${targetAppliance?.name ?: "Appliance"} Eco-Offset applied"
                    )
                }
                viewModelScope.launch {
                    firestoreRepository.updateApplianceEcoTip(action.applianceId, true)
                }
            }

            is EnergyUiAction.ApplyAutoPowerOptimization -> {
                _uiState.update { current ->
                    if (current.intelligence.isOptimizationApplied) {
                        current
                    } else {
                        val optimizedDemand = (current.powerFlux.totalDemandKw - current.intelligence.optimizationSavingsKw)
                            .coerceAtLeast(1.0)
                        val updatedFlux = current.powerFlux.copy(
                            totalDemandKw = (optimizedDemand * 100).toInt() / 100.0,
                            selfSustainedPercent = 68,
                            demandArcFraction = 0.60f
                        )
                        val updatedIntelligence = current.intelligence.copy(
                            isOptimizationApplied = true,
                            ecoScore = 98
                        )
                        current.copy(
                            powerFlux = updatedFlux,
                            intelligence = updatedIntelligence,
                            userFeedback = "Optimization Engaged • 0.4 kWh Preserved"
                        )
                    }
                }
            }

            is EnergyUiAction.ClearFeedback -> {
                _uiState.update { it.copy(userFeedback = null) }
            }
        }
    }
}
