package com.example.music_helper.domain

import com.example.music_helper.data.model.AppInfo

interface GetInstalledAppsUseCase {
    suspend operator fun invoke(): Result<List<AppInfo>>
}
