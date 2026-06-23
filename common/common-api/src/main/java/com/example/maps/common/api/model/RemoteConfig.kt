package com.example.maps.common.api.model

interface RemoteConfig {
    suspend fun geAiModel(): String
}