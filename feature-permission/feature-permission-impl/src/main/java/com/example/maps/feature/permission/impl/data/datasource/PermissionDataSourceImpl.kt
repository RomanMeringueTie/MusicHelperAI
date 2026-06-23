package com.example.maps.feature.permission.impl.data.datasource

import android.content.ContentResolver
import android.os.Build
import android.provider.Settings.Secure.getString
import androidx.annotation.RequiresApi
import com.example.maps.feature.permission.api.data.datasource.PermissionDataSource

class PermissionDataSourceImpl(private val contentResolver: ContentResolver) :
    PermissionDataSource {

    private companion object {
        const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        const val MY_APP_PACKAGE = "com.example.maps"
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    override suspend fun get(): Boolean {
        val enabledListeners = getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        return enabledListeners?.contains(MY_APP_PACKAGE) == true
    }
}
