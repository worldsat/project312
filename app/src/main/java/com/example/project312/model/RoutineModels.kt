package com.example.project312.model

import androidx.compose.runtime.Immutable

@Immutable
data class SynapseStats(
    val triggersFiredCount: Int = 24,
    val triggersSubtitle: String = "Actions Today",
    val successVectorPercent: Double = 99.8,
    val successSubtitle: String = "% nominal",
    val isNeuralSynced: Boolean = true
)

enum class RoutineActionAccent {
    CYAN,
    PURPLE,
    EMERALD,
    CRIMSON,
    SLATE
}

enum class RoutineActionIcon {
    LIGHT_GRADUAL,
    BLINDS,
    BROADCAST,
    COFFEE,
    BLUE_LIGHT,
    ANC,
    DND,
    AIRFLOW,
    AMBER_LIGHT,
    PERIMETER,
    THERMOSTAT,
    SHADES,
    LOCK,
    RADAR,
    CAMERA,
    POWER_CUT
}

@Immutable
data class RoutineActionItem(
    val label: String,
    val iconType: RoutineActionIcon,
    val accent: RoutineActionAccent
)

enum class RoutineTriggerIcon {
    ALARM,
    NFC,
    TWILIGHT,
    BEDTIME
}

enum class RoutineCategoryIcon {
    SUNRISE,
    MATRIX,
    SUNSET,
    SECURITY
}

@Immutable
data class ScheduledSequenceItem(
    val id: String,
    val title: String,
    val triggerText: String,
    val triggerIcon: RoutineTriggerIcon,
    val categoryIcon: RoutineCategoryIcon,
    val categoryAccent: RoutineActionAccent,
    val isEnabled: Boolean,
    val actions: List<RoutineActionItem>
)

@Immutable
data class RoutinesUiState(
    val synapseStats: SynapseStats = SynapseStats(),
    val routines: List<ScheduledSequenceItem> = emptyList(),
    val isSortedAlphabetical: Boolean = false,
    val userFeedback: String? = null
)

sealed interface RoutinesUiAction {
    data class ToggleRoutine(val routineId: String) : RoutinesUiAction
    data object DeployNewRoutine : RoutinesUiAction
    data object ToggleSortMatrix : RoutinesUiAction
    data object ClearFeedback : RoutinesUiAction
}
