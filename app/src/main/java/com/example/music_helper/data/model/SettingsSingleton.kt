package com.example.music_helper.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SettingsSingleton {
    var isDarkTheme by mutableStateOf(false)
    var isNotificationsEnabled by mutableStateOf(false)
    var isGuest by mutableStateOf(true)
    var isPermissionGiven by mutableStateOf(false)
    var isFirstRun by mutableStateOf(true)
}