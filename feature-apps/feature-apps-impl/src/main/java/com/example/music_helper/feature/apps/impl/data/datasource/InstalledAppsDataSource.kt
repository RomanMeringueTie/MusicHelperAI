package com.example.music_helper.feature.apps.impl.data.datasource

import com.example.music_helper.feature.apps.api.model.AppInfo

interface InstalledAppsDataSource {
    suspend fun get(): List<AppInfo>
}
