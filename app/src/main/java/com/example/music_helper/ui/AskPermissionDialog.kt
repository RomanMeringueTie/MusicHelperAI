package com.example.music_helper.ui

import android.content.Context
import android.content.Intent
import android.provider.Settings.Secure.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.music_helper.R
import com.example.music_helper.common.api.model.SettingsSingleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskPermissionDialog(
    onDismissRequest: () -> Unit,
) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val isGranted = isNotificationPermissionGranted(context)
        SettingsSingleton.isPermissionGiven = isGranted
                onDismissRequest()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Выдать разрешение") },
        text = { Text("На доступ к уведомлениям") },
        confirmButton = {
            TextButton(onClick = {
                openNotificationSettings(launcher)
            }) {
                Text("В настройки")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )

}

@Preview
@Composable
private fun AskPermissionScreen_Preview() {
    AskPermissionDialog(onDismissRequest = {})
}

private fun openNotificationSettings(launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
    launcher.launch(intent)
}

private fun isNotificationPermissionGranted(context: Context): Boolean {
    val enabledNotificationListeners = getString(
        context.contentResolver,
        "enabled_notification_listeners"
    )
    return enabledNotificationListeners?.contains(context.packageName) == true
}