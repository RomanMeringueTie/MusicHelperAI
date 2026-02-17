package com.example.music_helper.feature.permission.api.domain

interface GetPermissionUseCase {
    suspend operator fun invoke(): Boolean
}