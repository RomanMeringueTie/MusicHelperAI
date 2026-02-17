package com.example.music_helper.feature.permission.api.data.datasource

interface PermissionDataSource {
    suspend fun get(): Boolean
}