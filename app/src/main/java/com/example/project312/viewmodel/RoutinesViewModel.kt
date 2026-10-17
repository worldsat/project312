package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project312.data.RoutineMockData
import com.example.project312.model.RoutinesUiAction
import com.example.project312.model.RoutinesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutinesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RoutineMockData.createInitialState())
    val uiState: StateFlow<RoutinesUiState> = _uiState.asStateFlow()

    fun onAction(action: RoutinesUiAction) {
        when (action) {
            is RoutinesUiAction.ToggleRoutine -> {
                _uiState.update { current ->
                    val updated = current.routines.map { routine ->
                        if (routine.id == action.routineId) {
                            routine.copy(isEnabled = !routine.isEnabled)
                        } else {
                            routine
                        }
                    }
                    val target = current.routines.find { it.id == action.routineId }
                    val stateText = if (target?.isEnabled == true) "Deactivated" else "Activated"
                    current.copy(
                        routines = updated,
                        userFeedback = "${target?.title ?: "Routine"} $stateText"
                    )
                }
            }

            is RoutinesUiAction.DeployNewRoutine -> {
                _uiState.update { current ->
                    current.copy(
                        userFeedback = "Routine Logic Architect: New Neural Sequence Template Ready"
                    )
                }
            }

            is RoutinesUiAction.ToggleSortMatrix -> {
                _uiState.update { current ->
                    val nextSort = !current.isSortedAlphabetical
                    val sortedList = if (nextSort) {
                        current.routines.sortedBy { it.title }
                    } else {
                        RoutineMockData.initialRoutines
                    }
                    current.copy(
                        routines = sortedList,
                        isSortedAlphabetical = nextSort,
                        userFeedback = if (nextSort) "Matrix Sorted: Alphabetical" else "Matrix Sorted: Execution Priority"
                    )
                }
            }

            is RoutinesUiAction.ClearFeedback -> {
                _uiState.update { it.copy(userFeedback = null) }
            }
        }
    }
}
