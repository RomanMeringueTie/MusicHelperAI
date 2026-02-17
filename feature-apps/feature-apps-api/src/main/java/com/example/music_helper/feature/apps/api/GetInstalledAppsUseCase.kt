package com.example.music_helper.feature.apps.api

import com.example.music_helper.feature.apps.api.AppInfo

interface GetInstalledAppsUseCase {
    suspend operator fun invoke(): Result<List<AppInfo>>
}
