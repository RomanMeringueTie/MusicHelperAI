package com.example.music_helper.common.api.model

interface RemoteConfig {
    suspend fun geAiModel(): String
}