package com.example.maps.domain

interface GetPickedAppsUseCase {
    operator fun invoke(): Result<Set<String>>
}