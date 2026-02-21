package com.example.music_helper.feature.onboarding.api.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.music_helper.feature.onboarding.api.R
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.common.api.model.UserSingleton
import com.example.music_helper.feature.apps.api.model.AppInfo
import com.example.music_helper.feature.onboarding.api.presentation.OnboardingViewModel
import com.example.music_helper.feature.permission.api.ui.AskPermissionDialog
import com.example.music_helper.common.api.presentation.State

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Composable
fun OnboardingScreen(
    modifier: Modifier,
    viewModel: OnboardingViewModel,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    onRouteToNext: () -> Unit,
    onRouteToSignIn: () -> Unit,
) {

    val pickedApps = viewModel.pickedApps.collectAsState()
    val isPermissionDialogShown = viewModel.isPermissionDialogShown.collectAsState()

    FirstTimeRunScreenImpl(
        modifier = modifier,
        isDarkTheme = SettingsSingleton.isDarkTheme,
        onRouteToNext = onRouteToNext,
        onRouteToSignIn = onRouteToSignIn,
        onThemeChange = onThemeChange,
        onRouteToPickApps = onRouteToPickApps,
        pickedApps = pickedApps.value,
        isNotificationsAllowed = SettingsSingleton.isNotificationsEnabled,
        onNotificationSettingChange = viewModel::onNotificationSettingChange,
        isPermissionGiven = SettingsSingleton.isPermissionGiven,
        isPermissionDialogShown = isPermissionDialogShown.value,
        onChangePermissionDialogVisibility = viewModel::changePermissionDialogVisibility
    )
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Composable
private fun FirstTimeRunScreenImpl(
    modifier: Modifier,
    isDarkTheme: Boolean,
    onRouteToNext: () -> Unit,
    onRouteToSignIn: () -> Unit,
    onThemeChange: () -> Unit,
    onRouteToPickApps: () -> Unit,
    pickedApps: State<List<AppInfo>>,
    isNotificationsAllowed: Boolean?,
    onNotificationSettingChange: (Boolean) -> Unit,
    isPermissionGiven: Boolean,
    isPermissionDialogShown: Boolean,
    onChangePermissionDialogVisibility: () -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Здравствуйте!", style = MaterialTheme.typography.headlineLarge)
        Text("Настройте приложение как Вам удобно", style = MaterialTheme.typography.bodyLarge)

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
                    contentScale = ContentScale.Crop,
                    alpha = 1f
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

                TextButton(
                    onClick = onRouteToSignIn,
                ) {
                    Text(
                        stringResource(R.string.sign_in),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }

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
        ElevatedButton(
            onClick = onRouteToNext,
        ) {
            Text(text = "Поехали!", modifier = Modifier.padding(8.dp))
        }
    }

    if (isPermissionDialogShown) {
        AskPermissionDialog(
            onDismissRequest = onChangePermissionDialogVisibility
        )
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

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview(showBackground = true)
@Composable
private fun FirstTimeRunScreen_Preview() {
    FirstTimeRunScreenImpl(
        modifier = Modifier.fillMaxSize(),
        onRouteToNext = {},
        onRouteToSignIn = {},
        isDarkTheme = false,
        pickedApps = State.Content(emptyList()),
        isNotificationsAllowed = false,
        onNotificationSettingChange = {},
        isPermissionGiven = false,
        isPermissionDialogShown = false,
        onChangePermissionDialogVisibility = {},
        onThemeChange = {},
        onRouteToPickApps = {},
    )
}
