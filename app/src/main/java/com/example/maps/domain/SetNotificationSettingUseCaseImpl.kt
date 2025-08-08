package com.example.maps.domain

import android.content.SharedPreferences
import androidx.core.content.edit

class SetNotificationSettingUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    SetNotificationSettingUseCase {
    override suspend fun invoke(isAllowed: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean("NOTIFICATIONS", isAllowed)
            apply()
        }
    }
}