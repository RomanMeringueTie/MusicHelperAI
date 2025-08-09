package com.example.maps.data.datasource

interface PickedAppsDataSource {
    suspend fun get(): Set<String>
    suspend fun set(pickedApps: Set<String>)
}