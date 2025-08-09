package com.example.maps.domain

interface SetPickedAppsUseCase {
    suspend operator fun invoke(pickedApps: Set<String>)
}
