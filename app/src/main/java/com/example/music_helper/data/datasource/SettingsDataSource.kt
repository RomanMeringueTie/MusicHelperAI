package com.example.music_helper.data.datasource

interface SettingsDataSource {
    suspend fun save()

    suspend fun get()
}