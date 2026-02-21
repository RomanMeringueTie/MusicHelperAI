package com.example.music_helper.feature.apps.impl.data.datasource

interface PickedAppsDataSource {
    suspend fun get(): Set<String>
    suspend fun set(pickedApps: Set<String>)
}