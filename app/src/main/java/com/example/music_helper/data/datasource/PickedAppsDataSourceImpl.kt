package com.example.music_helper.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class PickedAppsDataSourceImpl(private val sharedPreferences: SharedPreferences) :
    PickedAppsDataSource {

        private companion object {
            const val PICKED_APPS_KEY = "PICKED_APPS"
        }

    override suspend fun get(): Set<String> {
        val result = sharedPreferences.getStringSet(PICKED_APPS_KEY, emptySet<String>())
        return result ?: emptySet()
    }

    override suspend fun set(pickedApps: Set<String>) {
        sharedPreferences.edit(commit = true) {
            putStringSet(PICKED_APPS_KEY, pickedApps)
            apply()
        }
    }
}