package com.example.music_helper.feature.apps.impl.domain

import com.example.music_helper.feature.apps.api.domain.GetPickedAppsUseCase
import com.example.music_helper.feature.apps.impl.data.datasource.PickedAppsDataSource

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
