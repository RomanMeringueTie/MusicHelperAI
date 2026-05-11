package com.example.music_helper.common.impl.config

import com.example.music_helper.common.api.client.HttpClient
import com.example.music_helper.common.api.model.RemoteConfig

class RemoteConfigImpl(
    private val httpClient: HttpClient,
) : RemoteConfig {

    override suspend fun geAiModel(): String {
        val configResponse = httpClient.get("/config", ConfigResponse::class)
        return configResponse.aiModel
    }
}
