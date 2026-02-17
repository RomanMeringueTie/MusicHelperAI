package com.example.music_helper.feature.settings.impl.domain

import com.example.music_helper.feature.settings.impl.data.datasource.PickedAppsDataSource
import com.example.music_helper.feature.settings.api.domain.SetPickedAppsUseCase

class SetPickedAppsUseCaseImpl(private val pickedAppsDataSource: PickedAppsDataSource) :
    SetPickedAppsUseCase {
    override suspend fun invoke(pickedApps: Set<String>) {
        pickedAppsDataSource.set(pickedApps)
    }
}
