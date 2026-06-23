package com.example.maps.feature.apps.impl.domain

import com.example.maps.feature.apps.impl.data.datasource.PickedAppsDataSource
import com.example.maps.feature.apps.api.domain.SetPickedAppsUseCase

class SetPickedAppsUseCaseImpl(private val pickedAppsDataSource: PickedAppsDataSource) :
    SetPickedAppsUseCase {
    override suspend fun invoke(pickedApps: Set<String>) {
        pickedAppsDataSource.set(pickedApps)
    }
}
