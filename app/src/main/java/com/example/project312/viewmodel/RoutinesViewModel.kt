package com.example.project312.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project312.data.FirebaseFirestoreRepository
import com.example.project312.model.RoutinesUiAction
import com.example.project312.model.RoutinesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutinesViewModel(
    private val firestoreRepository: FirebaseFirestoreRepository = FirebaseFirestoreRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutinesUiState())
    val uiState: StateFlow<RoutinesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firestoreRepository.observeRoutines().collect { cloudRoutines ->
                if (cloudRoutines.isNotEmpty()) {
                    _uiState.update { it.copy(routines = cloudRoutines) }
                }
            }
        }
    }

    fun onAction(action: RoutinesUiAction) {
        when (action) {
            is RoutinesUiAction.ToggleRoutine -> {
                val target = _uiState.value.routines.find { it.id == action.routineId }
                val nextState = if (target?.isEnabled == true) false else true
                _uiState.update { current ->
                    val updated = current.routines.map { routine ->
                        if (routine.id == action.routineId) {
                            routine.copy(isEnabled = nextState)
                        } else {
                            routine
                        }
                    }
                    val stateText = if (nextState) "Activated" else "Deactivated"
                    current.copy(
                        routines = updated,
                        userFeedback = "${target?.title ?: "Routine"} $stateText"
                    )
                }
                viewModelScope.launch {
                    firestoreRepository.updateRoutineEnabled(action.routineId, nextState)
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
                        current.routines.sortedBy { it.id }
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
