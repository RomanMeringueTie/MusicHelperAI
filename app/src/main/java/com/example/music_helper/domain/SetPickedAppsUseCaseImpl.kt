package com.example.music_helper.domain

import com.example.music_helper.data.datasource.PickedAppsDataSource

class SetPickedAppsUseCaseImpl(private val pickedAppsDataSource: PickedAppsDataSource) :
    SetPickedAppsUseCase {
    override suspend fun invoke(pickedApps: Set<String>) {
        pickedAppsDataSource.set(pickedApps)
    }
}