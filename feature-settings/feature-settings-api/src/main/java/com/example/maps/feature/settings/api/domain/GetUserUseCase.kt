package com.example.maps.feature.settings.api.domain

interface GetUserUseCase {
    suspend operator fun invoke(): String
}
