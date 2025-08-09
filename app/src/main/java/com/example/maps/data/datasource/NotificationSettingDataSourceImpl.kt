package com.example.maps.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class NotificationSettingDataSourceImpl(private val sharedPreferences: SharedPreferences) :
    NotificationSettingDataSource {
    override suspend fun get(): Boolean {
        return sharedPreferences.getBoolean("NOTIFICATIONS", false)
    }

    override suspend fun set(isAllowed: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean("NOTIFICATIONS", isAllowed)
            apply()
        }
    }

}