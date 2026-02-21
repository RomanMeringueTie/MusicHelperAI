package com.example.music_helper.feature.apps.impl.data.datasource

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.edit

class PickedAppsDataSourceImpl(private val sharedPreferences: SharedPreferences) :
    PickedAppsDataSource {

        private companion object {
            const val PICKED_APPS_KEY = "PICKED_APPS"
        }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override suspend fun get(): Set<String> {
        val result = sharedPreferences.getStringSet(PICKED_APPS_KEY, emptySet<String>())
        return result ?: emptySet()
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    override suspend fun set(pickedApps: Set<String>) {
        sharedPreferences.edit(commit = true) {
            putStringSet(PICKED_APPS_KEY, pickedApps)
            apply()
        }
    }
}
