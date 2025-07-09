package com.example.maps.domain

import android.content.ContentResolver

class GetPermissionUseCaseImpl(private val contentResolver: ContentResolver) :
    GetPermissionUseCase {
    override fun invoke(): Boolean {
        val enabledListeners = android.provider.Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )
        return enabledListeners?.contains("com.example.maps") == true
    }
}