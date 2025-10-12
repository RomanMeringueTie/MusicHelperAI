package com.example.maps.data.datasource

interface SettingsDataSource {
    suspend fun save()

    suspend fun get()
}