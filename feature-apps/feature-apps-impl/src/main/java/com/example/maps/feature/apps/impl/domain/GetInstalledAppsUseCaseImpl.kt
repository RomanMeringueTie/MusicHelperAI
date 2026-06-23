package com.example.maps.feature.apps.impl.domain

import com.example.maps.feature.apps.api.model.AppInfo
import com.example.maps.feature.apps.api.domain.GetInstalledAppsUseCase
import com.example.maps.feature.apps.impl.data.datasource.InstalledAppsDataSource

class GetInstalledAppsUseCaseImpl(
    private val installedAppsDataSource: InstalledAppsDataSource,
) :
    GetInstalledAppsUseCase {
    override suspend operator fun invoke(): Result<List<AppInfo>> {
        try {
            val result = installedAppsDataSource.get()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
