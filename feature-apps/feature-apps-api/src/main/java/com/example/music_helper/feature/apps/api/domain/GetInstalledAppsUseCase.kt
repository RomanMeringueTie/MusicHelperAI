package com.example.music_helper.feature.apps.api.domain

import com.example.music_helper.feature.apps.api.model.AppInfo

interface GetInstalledAppsUseCase {
    suspend operator fun invoke(): Result<List<AppInfo>>
}