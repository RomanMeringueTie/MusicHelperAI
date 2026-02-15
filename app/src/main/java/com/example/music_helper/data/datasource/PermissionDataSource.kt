package com.example.music_helper.data.datasource

interface PermissionDataSource {
    suspend fun get(): Boolean
}