package com.example.maps.domain

import android.content.SharedPreferences

class GetNotificationSettingUseCaseImpl(private val sharedPreferences: SharedPreferences) :
    GetNotificationSettingUseCase {
    override suspend operator fun invoke(): Boolean {
        val result = sharedPreferences.getBoolean("NOTIFICATIONS", false)
        return result
    }
}