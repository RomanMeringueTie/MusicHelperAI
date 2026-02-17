package com.example.music_helper.feature.settings.api.domain

interface GetPickedAppsUseCase {
    suspend operator fun invoke(): Result<Set<String>>
}
