package com.example.maps.feature.apps.impl.data.datasource

import com.example.maps.feature.apps.api.model.AppInfo

interface InstalledAppsDataSource {
    suspend fun get(): List<AppInfo>
}
