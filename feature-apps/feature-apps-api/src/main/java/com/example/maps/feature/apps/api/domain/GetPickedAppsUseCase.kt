package com.example.maps.feature.apps.api.domain

interface GetPickedAppsUseCase {
    suspend operator fun invoke(): Result<Set<String>>
}