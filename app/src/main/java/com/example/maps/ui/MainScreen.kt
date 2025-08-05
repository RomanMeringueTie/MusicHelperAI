package com.example.maps.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maps.presentation.MainViewModel
import com.example.maps.ui.theme.MapsTheme
import com.example.maps.ui.utils.EnterAnimation
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

    val isDarkTheme = viewModel.isDarkTheme.collectAsState()
    val isAppPicked = viewModel.isAppsPicked
    val navController = rememberNavController()
    var startDestination = if (isAppPicked) "LISTENS_LIST" else "PICK_APPS"

    MapsTheme(darkTheme = isDarkTheme.value) {
        Scaffold(modifier = modifier) { innerPadding ->
            NavHost(navController = navController, startDestination = "LOG_IN") {
                composable("LOG_IN") {
                    LoginScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onRoute = { navController.navigate(startDestination) }
                    )
                }
                composable("PICK_APPS") {
                    EnterAnimation {
                        PickAppsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            viewModel = koinViewModel(),
                            onRoute = { navController.navigate("LISTENS_LIST") }
                        )
                    }
                }
                composable("LISTENS_LIST") {
                    ListensListScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinViewModel(),
                        onRouteToSettings = { navController.navigate("SETTINGS") },
                        onAnalyze = {
                            navController.navigate("ANALYSIS")
                        },
                        onStats = { navController.navigate("STATS") }
                    )
                }
                composable("ANALYSIS") {
                    AnalysisScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinViewModel(),
                        onBack = navController::navigateUp
                    )
                }
                composable("STATS") {
                    EnterAnimation {
                        StatsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            viewModel = koinViewModel(),
                            onBack = navController::navigateUp
                        )
                    }
                }
                composable("SETTINGS") {
                    EnterAnimation {
                        SettingsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            onThemeChange = viewModel::changeTheme,
                            onRouteToPickApps = { navController.navigate("PICK_APPS") },
                            onBack = navController::navigateUp,
                            viewModel = koinViewModel(),
                        )
                    }
                }
            }
        }
    }
}