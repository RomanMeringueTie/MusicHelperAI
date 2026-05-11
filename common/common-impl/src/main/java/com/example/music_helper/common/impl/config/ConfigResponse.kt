package com.example.music_helper.common.impl.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    @SerialName("ai_model")
    val aiModel: String,
    @SerialName("updated_at")
    val updatedAt: String,
)
