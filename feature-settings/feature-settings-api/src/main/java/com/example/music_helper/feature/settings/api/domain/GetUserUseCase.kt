package com.example.music_helper.feature.settings.api.domain

interface GetUserUseCase {
    suspend operator fun invoke(): String
}
