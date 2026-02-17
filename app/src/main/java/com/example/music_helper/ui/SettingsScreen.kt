package com.example.music_helper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.music_helper.BuildConfig
import com.example.music_helper.R
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.common.api.model.UserSingleton
import com.example.music_helper.common.api.toggles.TogglesHolder
import com.example.music_helper.feature.apps.api.AppInfo
import com.example.music_helper.presentation.DebugPanelViewModel
import com.example.music_helper.presentation.SettingsViewModel
import com.example.music_helper.presentation.State
import com.example.music_helper.ui.utils.signOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    onBack: () -> Unit,
    onSignIn: () -> Unit,
    settingsViewModel: SettingsViewModel,
    debugPanelViewModel: DebugPanelViewModel,
) {
    val pickedApps = settingsViewModel.pickedApps.collectAsState()
    val isSignOutDialogShown = settingsViewModel.isSignOutDialogShown.collectAsState()
    val isPermissionDialogShown = settingsViewModel.isPermissionDialogShown.collectAsState()
    val isTogglesEnabled = debugPanelViewModel.isTogglesEnabled.collectAsState()

    SettingsScreenImpl(
        modifier = modifier,
        isDarkTheme = SettingsSingleton.isDarkTheme,
        onThemeChange = onThemeChange,
        onRouteToPickApps = onRouteToPickApps,
        onBack = onBack,
        pickedApps = pickedApps.value,
        isNotificationsAllowed = SettingsSingleton.isNotificationsEnabled,
        onNotificationSettingChange = settingsViewModel::onNotificationSettingChange,
        onSignIn = onSignIn,
        onSignOut = settingsViewModel::onSignOut,
        isSignOutDialogShown = isSignOutDialogShown.value,
        onChangeSignOutDialogVisibility = settingsViewModel::changeSignOutDialogVisibility,
        isPermissionGiven = SettingsSingleton.isPermissionGiven,
        isPermissionDialogShown = isPermissionDialogShown.value,
        onChangePermissionDialogVisibility = settingsViewModel::changePermissionDialogVisibility,
        isTogglesEnabled = isTogglesEnabled.value,
        onToggleStateChanged = debugPanelViewModel::changeToggleState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenImpl(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    onBack: () -> Unit,
    pickedApps: State<List<AppInfo>>,
    isNotificationsAllowed: Boolean?,
    onNotificationSettingChange: (Boolean) -> Unit,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    isSignOutDialogShown: Boolean,
    onChangeSignOutDialogVisibility: () -> Unit,
    isPermissionGiven: Boolean,
    isPermissionDialogShown: Boolean,
    onChangePermissionDialogVisibility: () -> Unit,
    isTogglesEnabled: List<Boolean>,
    onToggleStateChanged: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            key(MaterialTheme.colorScheme.background) {
                TopAppBar(
                    title = { Text(stringResource(R.string.settings)) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigate Back"
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingsSection(
                    title = stringResource(R.string.profile),
                    icon = Icons.Default.Person
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = UserSingleton.picture
                                ?: "https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png",
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = UserSingleton.name
                                    ?: stringResource(R.string.unknown_name),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = stringResource(R.string.user),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (UserSingleton.isAuthorized) {
                            TextButton(
                                onClick = onChangeSignOutDialogVisibility,
                            ) {
                                Text(
                                    stringResource(R.string.sign_out),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        } else {
                            TextButton(
                                onClick = onSignIn,
                            ) {
                                Text(
                                    stringResource(R.string.sign_in),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                    }
                }
            }

            item {
                SettingsSection(
                    title = stringResource(R.string.app),
                    icon = Icons.Default.Settings
                ) {
                    SettingsItem(
                        title = stringResource(R.string.change_theme),
                        subtitle = if (isDarkTheme) "Тёмная" else "Светлая",
                        icon = ImageVector.vectorResource(R.drawable.theme_icon),
                        onClick = onThemeChange
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    when (pickedApps) {
                        is State.Content -> {
                            SettingsItem(
                                title = "Выбрать приложения",
                                subtitle = getAppsSubtitle(pickedApps.data),
                                icon = Icons.AutoMirrored.Filled.List,
                                onClick = onRouteToPickApps
                            )
                        }

                        else -> {
                            SettingsItem(
                                title = "Выбрать приложения",
                                subtitle = "",
                                icon = Icons.AutoMirrored.Filled.List,
                                onClick = onRouteToPickApps
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    SettingsItem(
                        title = "Разрешение",
                        subtitle = "На получение данных из уведомлений: ${if (isPermissionGiven) "Выдано" else "Не выдано"}",
                        icon = Icons.AutoMirrored.Filled.Send,
                        onClick = onChangePermissionDialogVisibility
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    SettingsItemWithSwitch(
                        title = "Уведомления",
                        subtitle = "Хотите получать уведомления?",
                        icon = Icons.Default.Notifications,
                        onClick = onNotificationSettingChange,
                        isChecked = isNotificationsAllowed
                    )
                }
            }
            if (BuildConfig.DEBUG) {
                item {
                    DebugPanel(isTogglesEnabled, onToggleStateChanged)
                }
            }
        }
        if (isSignOutDialogShown) {
            SignOutDialog(
                onConfirm = { signOut(context); onSignOut() },
                onDismiss = onChangeSignOutDialogVisibility
            )
        }

        if (isPermissionDialogShown) {
            AskPermissionDialog(
                onDismissRequest = onChangePermissionDialogVisibility
            )
        }
    }
}

@Composable
private fun DebugPanel(isTogglesEnabled: List<Boolean>, onToggleStateChanged: () -> Unit) {
    val toggles = TogglesHolder.getAll()

    SettingsSection(
        title = stringResource(R.string.debug_panel),
        icon = Icons.Default.Build
    ) {
        toggles.forEachIndexed { index, toggle ->
            SettingsItemWithSwitch(
                title = toggle.title,
                subtitle = toggle.description,
                icon = Icons.Default.Build,
                onClick = { toggle.changeState(); onToggleStateChanged() },
                isChecked = isTogglesEnabled[index]
            )
            if (toggle != toggles.last()) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            content()
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Setting Item Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle.isNotBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Сейчас выбраны: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    LinearProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun SettingsItemWithSwitch(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: (Boolean) -> Unit,
    isChecked: Boolean?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Setting Item Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        isChecked?.let {
            Switch(
                checked = it,
                onCheckedChange = onClick,
            )
        }
    }

}

@Composable
private fun getAppsSubtitle(apps: List<AppInfo>): String {
    return if (apps.isEmpty()) {
        stringResource(R.string.apps_not_picked)
    } else {
        stringResource(R.string.now_picked_apps, apps.joinToString(", ") { it.appName })
    }
}

@Composable
private fun SignOutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выйти из аккаунта") },
        text = { Text("Вы уверены?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.sign_out))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}