package com.example.music_helper.feature.apps.impl.di

import com.example.music_helper.feature.apps.api.GetInstalledAppsUseCase
import com.example.music_helper.feature.apps.impl.data.datasource.InstalledAppsDataSource
import com.example.music_helper.feature.apps.impl.data.datasource.InstalledAppsDataSourceImpl
import com.example.music_helper.feature.apps.impl.domain.GetInstalledAppsUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appsFeatureModule = module {
    singleOf(::InstalledAppsDataSourceImpl) { bind<InstalledAppsDataSource>() }
    singleOf(::GetInstalledAppsUseCaseImpl) { bind<GetInstalledAppsUseCase>() }
}
