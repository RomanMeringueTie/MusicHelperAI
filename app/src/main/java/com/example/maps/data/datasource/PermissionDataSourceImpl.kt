package com.example.maps.data.datasource

import android.content.ContentResolver
import android.provider.Settings.Secure.getString

class PermissionDataSourceImpl(private val contentResolver: ContentResolver) :
    PermissionDataSource {

    private companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        const val MY_APP_PACKAGE = "com.example.maps"
    }

    override suspend fun get(): Boolean {
        val enabledListeners = getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        return enabledListeners?.contains(MY_APP_PACKAGE) == true
    }
}