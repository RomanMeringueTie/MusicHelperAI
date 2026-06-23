package com.example.maps.feature.settings.api.domain

interface SaveUserUseCase {
    suspend operator fun invoke(userId: String)
}
