package com.example.music_helper.domain

interface GetPermissionUseCase {
    suspend operator fun invoke(): Boolean
}