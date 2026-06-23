package com.example.maps.domain

internal interface SendAnalyticsEventUseCase {
    suspend operator fun invoke(screenName: String, userId: String)
}
