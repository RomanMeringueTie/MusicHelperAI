package com.example.music_helper.feature.settings.impl.data.datasource

interface PickedAppsDataSource {
    suspend fun get(): Set<String>
    suspend fun set(pickedApps: Set<String>)
}