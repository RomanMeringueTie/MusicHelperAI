package com.example.maps.domain

interface SetNotificationSettingUseCase {
    suspend operator fun invoke(isAllowed: Boolean)
}