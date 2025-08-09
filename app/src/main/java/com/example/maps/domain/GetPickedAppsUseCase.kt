package com.example.maps.domain

interface GetPickedAppsUseCase {
    suspend operator fun invoke(): Result<Set<String>>
}