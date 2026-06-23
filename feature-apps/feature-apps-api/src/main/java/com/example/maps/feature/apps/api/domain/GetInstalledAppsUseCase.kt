package com.example.maps.feature.apps.api.domain

import com.example.maps.feature.apps.api.model.AppInfo

interface GetInstalledAppsUseCase {
    suspend operator fun invoke(): Result<List<AppInfo>>
}