package com.example.maps.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class PickedAppsDataSourceImpl(private val sharedPreferences: SharedPreferences) :
    PickedAppsDataSource {
    override suspend fun get(): Set<String> {
        val result = sharedPreferences.getStringSet("PICKED_APPS", emptySet<String>())
        return result ?: emptySet()
    }

    override suspend fun set(pickedApps: Set<String>) {
        sharedPreferences.edit(commit = true) {
            putStringSet("PICKED_APPS", pickedApps)
            apply()
        }
    }
}