package com.example.project312.data

import com.example.project312.model.RoutineActionAccent
import com.example.project312.model.RoutineActionIcon
import com.example.project312.model.RoutineActionItem
import com.example.project312.model.RoutineCategoryIcon
import com.example.project312.model.RoutineTriggerIcon
import com.example.project312.model.RoutinesUiState
import com.example.project312.model.ScheduledSequenceItem
import com.example.project312.model.SynapseStats

object RoutineMockData {

    val initialSynapseStats = SynapseStats(
        triggersFiredCount = 24,
        triggersSubtitle = "Actions Today",
        successVectorPercent = 99.8,
        successSubtitle = "% nominal",
        isNeuralSynced = true
    )

    val initialRoutines = listOf(
        ScheduledSequenceItem(
            id = "sunrise_awakening",
            title = "Sunrise Awakening",
            triggerText = "06:30 AM Daily",
            triggerIcon = RoutineTriggerIcon.ALARM,
            categoryIcon = RoutineCategoryIcon.SUNRISE,
            categoryAccent = RoutineActionAccent.EMERALD,
            isEnabled = true,
            actions = listOf(
                RoutineActionItem("Gradual 10% → 100%", RoutineActionIcon.LIGHT_GRADUAL, RoutineActionAccent.EMERALD),
                RoutineActionItem("Blinds Open 75%", RoutineActionIcon.BLINDS, RoutineActionAccent.CYAN),
                RoutineActionItem("Morning Broadcast", RoutineActionIcon.BROADCAST, RoutineActionAccent.PURPLE),
                RoutineActionItem("Espresso Brew Start", RoutineActionIcon.COFFEE, RoutineActionAccent.EMERALD)
            )
        ),
        ScheduledSequenceItem(
            id = "focus_matrix",
            title = "Focus Matrix",
            triggerText = "Trigger: NFC Desk Tag",
            triggerIcon = RoutineTriggerIcon.NFC,
            categoryIcon = RoutineCategoryIcon.MATRIX,
            categoryAccent = RoutineActionAccent.CYAN,
            isEnabled = true,
            actions = listOf(
                RoutineActionItem("Blue Light 450nm", RoutineActionIcon.BLUE_LIGHT, RoutineActionAccent.CYAN),
                RoutineActionItem("ANC Level 100%", RoutineActionIcon.ANC, RoutineActionAccent.PURPLE),
                RoutineActionItem("DND Shield On", RoutineActionIcon.DND, RoutineActionAccent.CRIMSON),
                RoutineActionItem("Airflow 22°C Silent", RoutineActionIcon.AIRFLOW, RoutineActionAccent.EMERALD)
            )
        ),
        ScheduledSequenceItem(
            id = "sunset_transition",
            title = "Sunset Transition",
            triggerText = "Astronomical Twilight (18:45)",
            triggerIcon = RoutineTriggerIcon.TWILIGHT,
            categoryIcon = RoutineCategoryIcon.SUNSET,
            categoryAccent = RoutineActionAccent.PURPLE,
            isEnabled = true,
            actions = listOf(
                RoutineActionItem("Amber Tone 2700K", RoutineActionIcon.AMBER_LIGHT, RoutineActionAccent.EMERALD),
                RoutineActionItem("Perimeter Guard Arm", RoutineActionIcon.PERIMETER, RoutineActionAccent.CYAN),
                RoutineActionItem("Thermostat 21°C", RoutineActionIcon.THERMOSTAT, RoutineActionAccent.PURPLE),
                RoutineActionItem("Smart Shades 100%", RoutineActionIcon.SHADES, RoutineActionAccent.SLATE)
            )
        ),
        ScheduledSequenceItem(
            id = "cyber_security_lockout",
            title = "Cyber Security Lockout",
            triggerText = "11:30 PM Nightly",
            triggerIcon = RoutineTriggerIcon.BEDTIME,
            categoryIcon = RoutineCategoryIcon.SECURITY,
            categoryAccent = RoutineActionAccent.CRIMSON,
            isEnabled = true,
            actions = listOf(
                RoutineActionItem("Biometric Locks On", RoutineActionIcon.LOCK, RoutineActionAccent.CRIMSON),
                RoutineActionItem("Motion Grid Armed", RoutineActionIcon.RADAR, RoutineActionAccent.CYAN),
                RoutineActionItem("IR Night Optical", RoutineActionIcon.CAMERA, RoutineActionAccent.EMERALD),
                RoutineActionItem("Vampire Loads Cut", RoutineActionIcon.POWER_CUT, RoutineActionAccent.PURPLE)
            )
        )
    )

    fun createInitialState(): RoutinesUiState {
        return RoutinesUiState(
            synapseStats = initialSynapseStats,
            routines = initialRoutines,
            isSortedAlphabetical = false,
            userFeedback = null
        )
    }
}
