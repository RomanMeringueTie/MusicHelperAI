package com.example.music_helper.feature.apps.api.domain

interface SetPickedAppsUseCase {
    suspend operator fun invoke(pickedApps: Set<String>)
}