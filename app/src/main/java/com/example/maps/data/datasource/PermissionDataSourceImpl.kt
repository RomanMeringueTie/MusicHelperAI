package com.example.maps.data.datasource

import android.content.ContentResolver

class PermissionDataSourceImpl(private val contentResolver: ContentResolver): PermissionDataSource {
    override suspend fun get(): Boolean {
        val enabledListeners = android.provider.Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )
        return enabledListeners?.contains("com.example.maps") == true
    }
}