package com.example.maps.domain

interface GetPermissionUseCase {
    suspend operator fun invoke(): Boolean
}