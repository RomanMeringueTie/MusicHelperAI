package com.example.maps.feature.permission.api.domain

interface GetPermissionUseCase {
    suspend operator fun invoke(): Boolean
}