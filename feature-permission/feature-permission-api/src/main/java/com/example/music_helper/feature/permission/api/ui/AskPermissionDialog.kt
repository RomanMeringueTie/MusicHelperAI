package com.example.music_helper.feature.permission.api.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.Secure.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.feature.permission.api.R

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
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
        title = { Text(stringResource(R.string.give_permission)) },
        text = { Text(stringResource(R.string.to_notifications)) },
        confirmButton = {
            TextButton(onClick = {
                openNotificationSettings(launcher)
            }) {
                Text(stringResource(R.string.to_settings))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    )

}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
@Preview
@Composable
private fun AskPermissionScreen_Preview() {
    AskPermissionDialog(onDismissRequest = {})
}

private fun openNotificationSettings(launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
    launcher.launch(intent)
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
private fun isNotificationPermissionGranted(context: Context): Boolean {
    val enabledNotificationListeners = getString(
        context.contentResolver,
        "enabled_notification_listeners"
    )
    return enabledNotificationListeners?.contains(context.packageName) == true
}