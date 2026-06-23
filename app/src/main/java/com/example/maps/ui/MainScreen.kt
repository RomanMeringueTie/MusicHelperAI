package com.example.maps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.maps.common.api.model.SettingsSingleton
import com.example.maps.common.api.theme.MapsTheme
import com.example.maps.presentation.MainViewModel
import com.example.maps.common.api.ui.utils.EnterAnimation
import org.koin.androidx.compose.koinViewModel
import com.example.maps.feature.analysis.api.ui.AnalysisScreen
import com.example.maps.feature.apps.api.ui.PickAppsScreen
import com.example.maps.feature.auth.api.ui.LoginScreen
import com.example.maps.feature.listens.api.ui.ListensListScreen
import com.example.maps.feature.onboarding.api.ui.OnboardingScreen
import com.example.maps.feature.settings.api.ui.SettingsScreen
import com.example.maps.feature.stats.api.ui.StatsScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
internal fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

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
                    LaunchedEffect(Unit) { viewModel.sendEvent("FIRST") }
                    EnterAnimation {
                        OnboardingScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            onRouteToNext = {
                                navController.navigate("LISTENS_LIST") {
                                    popUpTo("FIRST") { inclusive = true }
                                }
                            },
                            onRouteToSignIn = { navController.navigate("LOG_IN") },
                            viewModel = koinViewModel(),
                            onThemeChange = viewModel::changeTheme,
                            onRouteToPickApps = { navController.navigate("PICK_APPS") }
                        )
                    }
                }
                composable("LOG_IN") {
                    LaunchedEffect(Unit) { viewModel.sendEvent("LOG_IN") }
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
                    LaunchedEffect(Unit) { viewModel.sendEvent("PICK_APPS") }
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
                    LaunchedEffect(Unit) { viewModel.sendEvent("LISTENS_LIST") }
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
                    LaunchedEffect(Unit) { viewModel.sendEvent("ANALYSIS") }
                    AnalysisScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinViewModel(),
                        onBack = navController::navigateUp
                    )
                }
                composable("STATS") {
                    LaunchedEffect(Unit) { viewModel.sendEvent("STATS") }
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
                    LaunchedEffect(Unit) { viewModel.sendEvent("SETTINGS") }
                    EnterAnimation {
                        SettingsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            onThemeChange = viewModel::changeTheme,
                            onRouteToPickApps = { navController.navigate("PICK_APPS") },
                            onBack = navController::navigateUp,
                            settingsViewModel = koinViewModel(),
                            onSignIn = { navController.navigate("LOG_IN") },
                            debugPanelViewModel = koinViewModel()
                        )
                    }
                }
            }
        }
    }
}