package com.example.project312

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.project312.model.NavDestination
import com.example.project312.model.OverviewUiAction
import com.example.project312.navigation.NexusNavHost
import com.example.project312.navigation.Screen
import com.example.project312.navigation.navigateToDestination
import com.example.project312.ui.components.NexusBottomNavBar
import com.example.project312.ui.components.NexusTopAppBar
import com.example.project312.ui.theme.NexusHabitatTheme
import com.example.project312.viewmodel.EnergyViewModel
import com.example.project312.viewmodel.OverviewViewModel
import com.example.project312.viewmodel.RoomsViewModel
import com.example.project312.viewmodel.RoutinesViewModel
import com.example.project312.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NexusHabitatTheme {
                NexusHabitatApp()
            }
        }
    }
}

@Composable
fun NexusHabitatApp(
    overviewViewModel: OverviewViewModel = viewModel(),
    energyViewModel: EnergyViewModel = viewModel(),
    routinesViewModel: RoutinesViewModel = viewModel(),
    roomsViewModel: RoomsViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val navController = rememberNavController()
    val overviewState by overviewViewModel.uiState.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentDestination = when (currentRoute) {
        Screen.Energy.route -> NavDestination.ENERGY
        Screen.Rooms.route -> NavDestination.ROOMS
        Screen.Routines.route -> NavDestination.ROUTINES
        Screen.Settings.route -> NavDestination.SETTINGS
        else -> NavDestination.OVERVIEW
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NexusTopAppBar(
                habitatName = overviewState.telemetry.habitatName,
                statusText = overviewState.telemetry.overallStatus,
                temperatureText = "${overviewState.telemetry.overallTemp.toInt()}°C",
                hasUnreadNotification = overviewState.hasUnreadNotifications,
                onNotificationClick = {
                    overviewViewModel.onAction(OverviewUiAction.ToggleNotifications)
                },
                onAvatarClick = {
                    overviewViewModel.onAction(OverviewUiAction.OpenUserProfile)
                }
            )
        },
        bottomBar = {
            NexusBottomNavBar(
                currentDestination = currentDestination,
                onDestinationSelected = { destination ->
                    navController.navigateToDestination(destination)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NexusNavHost(
            navController = navController,
            overviewViewModel = overviewViewModel,
            energyViewModel = energyViewModel,
            routinesViewModel = routinesViewModel,
            roomsViewModel = roomsViewModel,
            settingsViewModel = settingsViewModel,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}