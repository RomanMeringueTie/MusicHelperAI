package com.example.music_helper.feature.permission.impl.di

import com.example.music_helper.feature.permission.api.domain.GetPermissionUseCase
import com.example.music_helper.feature.permission.api.data.datasource.PermissionDataSource
import com.example.music_helper.feature.permission.impl.data.datasource.PermissionDataSourceImpl
import com.example.music_helper.feature.permission.impl.domain.GetPermissionUseCaseImpl
import com.example.music_helper.feature.permission.api.presentation.AskPermissionViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val permissionFeatureModule = module {
    singleOf(::PermissionDataSourceImpl) { bind<PermissionDataSource>() }
    singleOf(::GetPermissionUseCaseImpl) { bind<GetPermissionUseCase>() }

    viewModelOf(::AskPermissionViewModel)
}
