package com.example.maps.common.api.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

object SettingsSingleton {
    var isDarkTheme by mutableStateOf(false)
    var isNotificationsEnabled by mutableStateOf(false)
    var isGuest by mutableStateOf(true)
    var isPermissionGiven by mutableStateOf(false)
    var isFirstRun by mutableStateOf(true)
}
