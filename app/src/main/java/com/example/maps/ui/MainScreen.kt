package com.example.maps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maps.data.model.SettingsSingleton
import com.example.maps.presentation.MainViewModel
import com.example.maps.ui.theme.MapsTheme
import com.example.maps.ui.utils.EnterAnimation
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

    val navController = rememberNavController()
    val isFirstTime = SettingsSingleton.isFirstRun
    val startDestination by lazy {
        if (isFirstTime) {
            "FIRST"
        } else if (SettingsSingleton.isGuest) {
            "LISTENS_LIST"
        } else "LOG_IN"
    }

    MapsTheme(darkTheme = SettingsSingleton.isDarkTheme) {
        Scaffold(modifier = modifier) { innerPadding ->
            NavHost(navController = navController, startDestination = startDestination) {
                composable("FIRST") {
                    FirstTimeRunScreen(
                        onRouteToNext = { navController.navigate("LISTENS_LIST") }
                    )
                }
                composable("LOG_IN") {
                    LoginScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinViewModel(),
                        onRoute = {
                            navController.navigate("LISTENS_LIST") {
                                popUpTo("LOG_IN") { inclusive = true }
                            }
                        }
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
                        onListensAnalyze = {
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
                            onSignIn = { navController.navigate("LOG_IN") }
                        )
                    }
                }
            }
        }
    }
}