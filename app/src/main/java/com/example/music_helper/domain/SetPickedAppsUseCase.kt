package com.example.music_helper.domain

interface SetPickedAppsUseCase {
    suspend operator fun invoke(pickedApps: Set<String>)
}
