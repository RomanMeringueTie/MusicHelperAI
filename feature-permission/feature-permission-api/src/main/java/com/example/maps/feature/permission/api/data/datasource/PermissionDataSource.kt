package com.example.maps.feature.permission.api.data.datasource

interface PermissionDataSource {
    suspend fun get(): Boolean
}