package com.example.maps.domain

import com.example.maps.data.model.AppInfo

interface GetInstalledAppsUseCase {
    suspend operator fun invoke(): Result<List<AppInfo>>
}
