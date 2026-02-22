package com.example.music_helper.feature.apps.impl.di

import com.example.music_helper.feature.apps.api.domain.GetInstalledAppsUseCase
import com.example.music_helper.feature.apps.api.domain.GetPickedAppsUseCase
import com.example.music_helper.feature.apps.api.domain.SetPickedAppsUseCase
import com.example.music_helper.feature.apps.impl.data.datasource.InstalledAppsDataSource
import com.example.music_helper.feature.apps.impl.data.datasource.InstalledAppsDataSourceImpl
import com.example.music_helper.feature.apps.impl.data.datasource.PickedAppsDataSource
import com.example.music_helper.feature.apps.impl.data.datasource.PickedAppsDataSourceImpl
import com.example.music_helper.feature.apps.impl.domain.GetInstalledAppsUseCaseImpl
import com.example.music_helper.feature.apps.impl.domain.GetPickedAppsUseCaseImpl
import com.example.music_helper.feature.apps.impl.domain.SetPickedAppsUseCaseImpl
import com.example.music_helper.feature.apps.api.presentation.PickAppsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appsFeatureModule = module {
    singleOf(::PickedAppsDataSourceImpl) { bind<PickedAppsDataSource>() }
    singleOf(::InstalledAppsDataSourceImpl) { bind<InstalledAppsDataSource>() }
    singleOf(::GetInstalledAppsUseCaseImpl) { bind<GetInstalledAppsUseCase>() }
    singleOf(::GetPickedAppsUseCaseImpl) { bind<GetPickedAppsUseCase>() }
    singleOf(::SetPickedAppsUseCaseImpl) { bind<SetPickedAppsUseCase>() }

    viewModelOf(::PickAppsViewModel)
}
