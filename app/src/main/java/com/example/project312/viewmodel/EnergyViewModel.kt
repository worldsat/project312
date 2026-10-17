package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project312.data.EnergyMockData
import com.example.project312.model.EnergyUiAction
import com.example.project312.model.EnergyUiState
import com.example.project312.model.TimelinePeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EnergyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(EnergyMockData.createInitialState())
    val uiState: StateFlow<EnergyUiState> = _uiState.asStateFlow()

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
