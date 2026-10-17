package com.example.project312.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.project312.model.NavDestination
import com.example.project312.model.OverviewUiState
import com.example.project312.ui.screens.energy.EnergyAnalyticsScreen
import com.example.project312.ui.screens.overview.OverviewScreen
import com.example.project312.ui.screens.rooms.RoomsZonesScreen
import com.example.project312.ui.screens.routines.AutomationsRoutinesScreen
import com.example.project312.ui.screens.settings.SettingsScreen
import com.example.project312.viewmodel.EnergyViewModel
import com.example.project312.viewmodel.OverviewViewModel
import com.example.project312.viewmodel.RoomsViewModel
import com.example.project312.viewmodel.RoutinesViewModel
import com.example.project312.viewmodel.SettingsViewModel

sealed class Screen(val route: String, val destination: NavDestination) {
    data object Overview : Screen("overview", NavDestination.OVERVIEW)
    data object Rooms : Screen("rooms", NavDestination.ROOMS)
    data object Routines : Screen("routines", NavDestination.ROUTINES)
    data object Energy : Screen("energy", NavDestination.ENERGY)
    data object Settings : Screen("settings", NavDestination.SETTINGS)

    companion object {
        fun fromDestination(destination: NavDestination): Screen = when (destination) {
            NavDestination.OVERVIEW -> Overview
            NavDestination.ROOMS -> Rooms
            NavDestination.ROUTINES -> Routines
            NavDestination.ENERGY -> Energy
            NavDestination.SETTINGS -> Settings
        }
    }
}

/**
 * App Navigation Graph connecting all supported screens:
 * - /overview (Smart Home Overview)
 * - /energy (Energy Analytics)
 * - /routines (Automations & Routines)
 * - /rooms (Rooms & Zones)
 * - /settings (Habitat Matrix & Settings)
 */
@Composable
fun NexusNavHost(
    navController: NavHostController,
    overviewViewModel: OverviewViewModel,
    energyViewModel: EnergyViewModel,
    routinesViewModel: RoutinesViewModel,
    roomsViewModel: RoomsViewModel,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
    onShowFeedback: (String) -> Unit = {}
) {
    val overviewState = overviewViewModel.uiState.collectAsStateWithLifecycle().value
    val energyState = energyViewModel.uiState.collectAsStateWithLifecycle().value
    val routinesState = routinesViewModel.uiState.collectAsStateWithLifecycle().value
    val roomsState = roomsViewModel.uiState.collectAsStateWithLifecycle().value
    val settingsState = settingsViewModel.uiState.collectAsStateWithLifecycle().value

    NavHost(
        navController = navController,
        startDestination = Screen.Overview.route,
        modifier = modifier,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        // 1. First Screen: Smart Home Overview
        composable(Screen.Overview.route) {
            OverviewScreen(
                state = overviewState,
                onAction = overviewViewModel::onAction
            )
        }

        // 2. Second Screen: Energy Analytics
        composable(Screen.Energy.route) {
            EnergyAnalyticsScreen(
                state = energyState,
                onAction = energyViewModel::onAction
            )
        }

        // 3. Third Screen: Automations & Routines
        composable(Screen.Routines.route) {
            AutomationsRoutinesScreen(
                state = routinesState,
                onAction = routinesViewModel::onAction
            )
        }

        // 4. Fourth Screen: Rooms & Zones
        composable(Screen.Rooms.route) {
            RoomsZonesScreen(
                state = roomsState,
                onAction = roomsViewModel::onAction
            )
        }

        // 5. Fifth Screen: Habitat Matrix & Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                state = settingsState,
                onAction = settingsViewModel::onAction
            )
        }
    }
}

fun NavHostController.navigateToDestination(destination: NavDestination) {
    val screen = Screen.fromDestination(destination)

    navigate(screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
