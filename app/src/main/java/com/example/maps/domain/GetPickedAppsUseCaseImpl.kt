package com.example.maps.domain

import com.example.maps.data.datasource.PickedAppsDataSource

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