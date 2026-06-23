package com.example.maps.feature.settings.impl.data.datasource

interface UserDataSource {
    suspend fun get(): String
    suspend fun set(userId: String)
}