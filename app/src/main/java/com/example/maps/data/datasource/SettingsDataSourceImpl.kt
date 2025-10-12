package com.example.maps.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.maps.data.model.SettingsSingleton

class SettingsDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val permissionDataSource: PermissionDataSource,
) :
    SettingsDataSource {

    private companion object {
        const val IS_DARK_THEME_KEY = "SETTINGS_IS_DARK_THEME"
        const val IS_NOTIFICATIONS_ENABLED_KEY = "SETTINGS_IS_NOTIFICATIONS_ENABLED"
        const val IS_GUEST_KEY = "SETTINGS_IS_GUEST"
        const val IS_FIRST_RUN_KEY = "SETTINGS_IS_FIRST_RUN"
    }

    override suspend fun save() {
        sharedPreferences.edit(commit = true) {
            putBoolean(IS_DARK_THEME_KEY, SettingsSingleton.isDarkTheme)
            putBoolean(IS_NOTIFICATIONS_ENABLED_KEY, SettingsSingleton.isNotificationsEnabled)
            putBoolean(IS_GUEST_KEY, SettingsSingleton.isGuest)
            putBoolean(IS_FIRST_RUN_KEY, false)
            apply()
        }
    }

    override suspend fun get() {
        SettingsSingleton.apply {
            isDarkTheme = sharedPreferences.getBoolean(IS_DARK_THEME_KEY, false)
            isNotificationsEnabled = sharedPreferences.getBoolean(IS_NOTIFICATIONS_ENABLED_KEY, false)
            isGuest = sharedPreferences.getBoolean(IS_GUEST_KEY, true)
            isPermissionGiven = permissionDataSource.get()
            isFirstRun = sharedPreferences.getBoolean(IS_FIRST_RUN_KEY, true)
        }
    }

}