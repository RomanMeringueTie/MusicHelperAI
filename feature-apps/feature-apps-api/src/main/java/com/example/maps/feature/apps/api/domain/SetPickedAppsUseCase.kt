package com.example.maps.feature.apps.api.domain

interface SetPickedAppsUseCase {
    suspend operator fun invoke(pickedApps: Set<String>)
}