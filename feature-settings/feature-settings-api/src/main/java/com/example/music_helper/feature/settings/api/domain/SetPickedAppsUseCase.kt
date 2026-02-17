package com.example.music_helper.feature.settings.api.domain

interface SetPickedAppsUseCase {
    suspend operator fun invoke(pickedApps: Set<String>)
}
