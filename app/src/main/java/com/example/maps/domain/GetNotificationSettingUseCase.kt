package com.example.maps.domain

interface GetNotificationSettingUseCase {
    suspend operator fun invoke(): Boolean
}