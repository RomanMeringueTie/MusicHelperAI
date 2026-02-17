package com.example.music_helper.feature.settings.api.domain

interface SaveUserUseCase {
    suspend operator fun invoke(userId: String)
}
