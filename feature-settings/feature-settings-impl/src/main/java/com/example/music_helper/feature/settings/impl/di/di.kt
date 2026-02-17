package com.example.music_helper.feature.settings.impl.di

import com.example.music_helper.feature.settings.api.data.datasource.SettingsDataSource
import com.example.music_helper.feature.settings.api.domain.GetPickedAppsUseCase
import com.example.music_helper.feature.settings.api.domain.GetUserUseCase
import com.example.music_helper.feature.settings.api.domain.SaveUserUseCase
import com.example.music_helper.feature.settings.api.domain.SetPickedAppsUseCase
import com.example.music_helper.feature.settings.impl.data.datasource.PickedAppsDataSource
import com.example.music_helper.feature.settings.impl.data.datasource.PickedAppsDataSourceImpl
import com.example.music_helper.feature.settings.impl.data.datasource.SettingsDataSourceImpl
import com.example.music_helper.feature.settings.impl.data.datasource.UserDataSource
import com.example.music_helper.feature.settings.impl.data.datasource.UserDataSourceImpl
import com.example.music_helper.feature.settings.impl.domain.GetPickedAppsUseCaseImpl
import com.example.music_helper.feature.settings.impl.domain.GetUserUseCaseImpl
import com.example.music_helper.feature.settings.impl.domain.SaveUserUseCaseImpl
import com.example.music_helper.feature.settings.impl.domain.SetPickedAppsUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsFeatureModule = module {
    singleOf(::PickedAppsDataSourceImpl) { bind<PickedAppsDataSource>() }
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::SettingsDataSourceImpl) { bind<SettingsDataSource>() }
    singleOf(::GetPickedAppsUseCaseImpl) { bind<GetPickedAppsUseCase>() }
    singleOf(::SetPickedAppsUseCaseImpl) { bind<SetPickedAppsUseCase>() }
    singleOf(::SaveUserUseCaseImpl) { bind<SaveUserUseCase>() }
    singleOf(::GetUserUseCaseImpl) { bind<GetUserUseCase>() }
}
