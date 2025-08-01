package com.example.maps.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.maps.R
import com.example.maps.data.model.AppInfo
import com.example.maps.data.model.UserModel
import com.example.maps.presentation.SettingsViewModel
import com.example.maps.presentation.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    onBack: () -> Unit,
    viewModel: SettingsViewModel,
) {

    val state = viewModel.state.collectAsState()

    SettingsScreenImpl(
        modifier = modifier,
        onThemeChange = onThemeChange,
        onRouteToPickApps = onRouteToPickApps,
        onBack = onBack,
        state = state.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenImpl(
    modifier: Modifier = Modifier,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    onBack: () -> Unit,
    state: State<List<AppInfo>>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                actions = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = UserModel.picture ?: "https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png",
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = UserModel.name ?: "Unknown Name",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onThemeChange() },
                colors = CardDefaults.cardColors()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.change_theme),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.light_dark),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRouteToPickApps() },
                colors = CardDefaults.cardColors()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.pick_app),
                        style = MaterialTheme.typography.titleMedium
                    )
                    when (state) {
                        is State.Content -> {
                            val pickedApps =
                                state.data.joinToString(separator = ", ") { app -> app.appName }
                            Text(
                                text = "Сейчас выбраны: $pickedApps",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}