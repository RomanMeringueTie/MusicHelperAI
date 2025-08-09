package com.example.maps.data.datasource

interface PermissionDataSource {
    suspend fun get(): Boolean
}