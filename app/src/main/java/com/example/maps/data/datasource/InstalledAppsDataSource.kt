package com.example.maps.data.datasource

import com.example.maps.data.model.AppInfo

interface InstalledAppsDataSource {
    suspend fun get(): List<AppInfo>
}