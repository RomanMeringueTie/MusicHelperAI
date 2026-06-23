package com.example.maps.feature.permission.impl.di

import com.example.maps.feature.permission.api.domain.GetPermissionUseCase
import com.example.maps.feature.permission.api.data.datasource.PermissionDataSource
import com.example.maps.feature.permission.impl.data.datasource.PermissionDataSourceImpl
import com.example.maps.feature.permission.impl.domain.GetPermissionUseCaseImpl
import com.example.maps.feature.permission.api.presentation.AskPermissionViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val permissionFeatureModule = module {
    singleOf(::PermissionDataSourceImpl) { bind<PermissionDataSource>() }
    singleOf(::GetPermissionUseCaseImpl) { bind<GetPermissionUseCase>() }

    viewModelOf(::AskPermissionViewModel)
}
