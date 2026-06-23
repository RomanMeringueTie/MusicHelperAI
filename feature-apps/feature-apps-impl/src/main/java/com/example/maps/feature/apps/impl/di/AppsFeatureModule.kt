package com.example.maps.feature.apps.impl.di

import com.example.maps.feature.apps.api.domain.GetInstalledAppsUseCase
import com.example.maps.feature.apps.api.domain.GetPickedAppsUseCase
import com.example.maps.feature.apps.api.domain.SetPickedAppsUseCase
import com.example.maps.feature.apps.impl.data.datasource.InstalledAppsDataSource
import com.example.maps.feature.apps.impl.data.datasource.InstalledAppsDataSourceImpl
import com.example.maps.feature.apps.impl.data.datasource.PickedAppsDataSource
import com.example.maps.feature.apps.impl.data.datasource.PickedAppsDataSourceImpl
import com.example.maps.feature.apps.impl.domain.GetInstalledAppsUseCaseImpl
import com.example.maps.feature.apps.impl.domain.GetPickedAppsUseCaseImpl
import com.example.maps.feature.apps.impl.domain.SetPickedAppsUseCaseImpl
import com.example.maps.feature.apps.api.presentation.PickAppsViewModel
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
