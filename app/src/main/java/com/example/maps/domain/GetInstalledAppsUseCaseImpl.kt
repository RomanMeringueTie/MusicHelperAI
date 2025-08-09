package com.example.maps.domain

import android.annotation.SuppressLint
import com.example.maps.data.model.AppInfo
import com.example.maps.data.datasource.InstalledAppsDataSource

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