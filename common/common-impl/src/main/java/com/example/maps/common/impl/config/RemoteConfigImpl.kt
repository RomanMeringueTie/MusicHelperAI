package com.example.maps.common.impl.config

import com.example.maps.common.api.client.HttpClient
import com.example.maps.common.api.model.RemoteConfig

class RemoteConfigImpl(
    private val httpClient: HttpClient,
) : RemoteConfig {

    override suspend fun geAiModel(): String {
        val configResponse = httpClient.get("https://iv222s06.hawk-dev.csc.sibsutis.ru/config", ConfigResponse::class)
        return configResponse.aiModel
    }
}
