package com.example.music_helper.domain

import android.annotation.SuppressLint
import com.example.music_helper.data.datasource.InstalledAppsDataSource
import com.example.music_helper.data.model.AppInfo

class GetInstalledAppsUseCaseImpl(
    private val installedAppsDataSource: InstalledAppsDataSource,
) :
    GetInstalledAppsUseCase {
    @SuppressLint("QueryPermissionsNeeded")
    override suspend operator fun invoke(): Result<List<AppInfo>> {
        try {
            val result = installedAppsDataSource.get()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}