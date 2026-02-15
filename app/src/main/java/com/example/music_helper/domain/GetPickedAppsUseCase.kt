package com.example.music_helper.domain

interface GetPickedAppsUseCase {
    suspend operator fun invoke(): Result<Set<String>>
}