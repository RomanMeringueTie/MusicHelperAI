package com.example.maps.domain

import com.example.maps.common.api.client.HttpClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class SendAnalyticsEventUseCaseImpl(private val httpClient: HttpClient) : SendAnalyticsEventUseCase {
    override suspend fun invoke(screenName: String, userId: String) {
        httpClient.post(
            arguments = "https://iv222s06-analytics.hawk-dev.csc.sibsutis.ru/events",
            body = Event(userId = userId, screenName = screenName),
            type = Any::class
        )
    }
}

@Serializable
data class Event(
    @SerialName("user_id")
    val userId: String,
    @SerialName("screen_name")
    val screenName: String,
)
