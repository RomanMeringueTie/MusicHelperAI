package com.example.music_helper.feature.settings.impl.di

import com.example.music_helper.feature.settings.api.data.datasource.SettingsDataSource
import com.example.music_helper.feature.settings.api.domain.GetUserUseCase
import com.example.music_helper.feature.settings.api.domain.SaveUserUseCase
import com.example.music_helper.feature.settings.api.presentation.DebugPanelViewModel
import com.example.music_helper.feature.settings.api.presentation.SettingsViewModel
import com.example.music_helper.feature.settings.impl.data.datasource.SettingsDataSourceImpl
import com.example.music_helper.feature.settings.impl.data.datasource.UserDataSource
import com.example.music_helper.feature.settings.impl.data.datasource.UserDataSourceImpl
import com.example.music_helper.feature.settings.impl.domain.GetUserUseCaseImpl
import com.example.music_helper.feature.settings.impl.domain.SaveUserUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsFeatureModule = module {
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::SettingsDataSourceImpl) { bind<SettingsDataSource>() }
    singleOf(::SaveUserUseCaseImpl) { bind<SaveUserUseCase>() }
    singleOf(::GetUserUseCaseImpl) { bind<GetUserUseCase>() }

    viewModelOf(::SettingsViewModel)
    viewModelOf(::DebugPanelViewModel)
}
