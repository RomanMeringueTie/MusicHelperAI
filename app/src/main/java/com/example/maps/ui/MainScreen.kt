package com.example.maps.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.maps.presentation.MainViewModel
import com.example.maps.ui.theme.MapsTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

    val isDarkTheme = viewModel.isDarkTheme.collectAsState()
    val isAppPicked = viewModel.isAppsPicked
    val navController = rememberNavController()
    var startDestination = if (isAppPicked) "LISTENS_LIST" else "PICK_APPS"

    MapsTheme(darkTheme = isDarkTheme.value) {
        Scaffold(modifier = modifier) { innerPadding ->
            NavHost(navController = navController, startDestination = startDestination) {
                composable("PICK_APPS") {
                    EnterAnimation {
                        PickAppsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            viewModel = koinInject(),
                            onRoute = { navController.navigate("LISTENS_LIST") }
                        )
                    }
                }
                composable("LISTENS_LIST") {
                    ListensListScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinInject(),
                        onRouteToSettings = { navController.navigate("SETTINGS") },
                        onClick = { listens: String ->
                            navController.navigate("ANALYSIS/$listens")
                        }
                    )
                }
                composable(route = "ANALYSIS/{listens}", arguments = listOf(navArgument("listens") {
                    type =
                        NavType.StringType
                })) {
                    val listens = it.arguments?.getString("listens")
                    AnalysisScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        viewModel = koinViewModel(parameters = {
                            parametersOf(
                                listens
                            )
                        }),
                    )
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