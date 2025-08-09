package com.example.maps.data.datasource

interface NotificationSettingDataSource {
    suspend fun get(): Boolean
    suspend fun set(isAllowed: Boolean)
}