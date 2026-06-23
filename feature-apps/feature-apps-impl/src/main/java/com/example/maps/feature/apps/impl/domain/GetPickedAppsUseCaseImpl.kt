package com.example.maps.feature.apps.impl.domain

import com.example.maps.feature.apps.api.domain.GetPickedAppsUseCase
import com.example.maps.feature.apps.impl.data.datasource.PickedAppsDataSource

class GetPickedAppsUseCaseImpl(private val pickedAppsDataSource: PickedAppsDataSource) :
    GetPickedAppsUseCase {
    override suspend fun invoke(): Result<Set<String>> {
        val result = pickedAppsDataSource.get()
        if (result.isEmpty()) {
            return Result.failure(Exception("No picked apps"))
        }
        return Result.success(result)
    }
}
