package com.example.music_helper.data.datasource

import com.example.music_helper.data.model.AppInfo

interface InstalledAppsDataSource {
    suspend fun get(): List<AppInfo>
}