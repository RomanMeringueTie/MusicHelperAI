package com.example.music_helper.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.common.api.theme.MapsTheme
import com.example.music_helper.presentation.MainViewModel
import com.example.music_helper.common.api.ui.utils.EnterAnimation
import org.koin.androidx.compose.koinViewModel
import com.example.music_helper.feature.analysis.api.ui.AnalysisScreen
import com.example.music_helper.feature.apps.api.ui.PickAppsScreen
import com.example.music_helper.feature.auth.api.ui.LoginScreen
import com.example.music_helper.feature.listens.api.ui.ListensListScreen
import com.example.music_helper.feature.onboarding.api.ui.OnboardingScreen
import com.example.music_helper.feature.settings.api.ui.SettingsScreen
import com.example.music_helper.feature.stats.api.ui.StatsScreen

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