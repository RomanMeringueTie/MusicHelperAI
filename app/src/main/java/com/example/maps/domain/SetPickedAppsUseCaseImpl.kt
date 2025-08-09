package com.example.maps.domain

import com.example.maps.data.datasource.PickedAppsDataSource

class SetPickedAppsUseCaseImpl(private val pickedAppsDataSource: PickedAppsDataSource) :
    SetPickedAppsUseCase {
    override suspend fun invoke(pickedApps: Set<String>) {
        pickedAppsDataSource.set(pickedApps)
    }
}