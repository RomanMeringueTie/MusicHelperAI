package com.example.maps.domain

interface SavePickedAppsUseCase {
    operator fun invoke(pickedApps: Set<String>)
}
