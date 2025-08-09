package com.example.maps.domain

import com.example.maps.data.datasource.NotificationSettingDataSource

class SetNotificationSettingUseCaseImpl(private val notificationSettingDataSource: NotificationSettingDataSource) :
    SetNotificationSettingUseCase {
    override suspend fun invoke(isAllowed: Boolean) {
        notificationSettingDataSource.set(isAllowed)
    }
}