package com.example.maps.feature.settings.api.data.datasource

interface SettingsDataSource {
    suspend fun save()
    suspend fun get()
}