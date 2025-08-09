package com.example.maps.domain

import com.example.maps.data.datasource.NotificationSettingDataSource

class GetNotificationSettingUseCaseImpl(private val notificationSettingDataSource: NotificationSettingDataSource) :
    GetNotificationSettingUseCase {
    override suspend operator fun invoke(): Boolean {
        val result = notificationSettingDataSource.get()
        return result
    }
}