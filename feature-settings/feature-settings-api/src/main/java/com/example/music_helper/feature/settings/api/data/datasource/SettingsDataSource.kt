package com.example.music_helper.feature.settings.api.data.datasource

interface SettingsDataSource {
    suspend fun save()
    suspend fun get()
}