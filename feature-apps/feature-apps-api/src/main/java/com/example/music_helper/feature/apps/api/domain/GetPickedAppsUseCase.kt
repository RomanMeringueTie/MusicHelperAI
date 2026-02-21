package com.example.music_helper.feature.apps.api.domain

interface GetPickedAppsUseCase {
    suspend operator fun invoke(): Result<Set<String>>
}