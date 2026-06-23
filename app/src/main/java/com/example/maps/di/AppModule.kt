package com.example.maps.di

import android.content.Context
import com.example.maps.common.api.client.HttpClient
import com.example.maps.common.impl.client.HttpClientImpl
import com.example.maps.common.impl.di.commonModule
import com.example.maps.domain.SendAnalyticsEventUseCase
import com.example.maps.domain.SendAnalyticsEventUseCaseImpl
import com.example.maps.feature.analysis.impl.di.analysisFeatureModule
import com.example.maps.feature.apps.impl.di.appsFeatureModule
import com.example.maps.feature.auth.impl.di.authFeatureModule
import com.example.maps.feature.listens.impl.di.listenFeatureModule
import com.example.maps.feature.onboarding.impl.di.onboardingFeatureModule
import com.example.maps.feature.permission.impl.di.permissionFeatureModule
import com.example.maps.feature.settings.impl.di.settingsFeatureModule
import com.example.maps.feature.stats.impl.di.statsFeatureModule
import com.example.maps.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // context
    single {
        androidContext().packageManager
    }
    single {
        androidContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    }
    single {
        androidContext().contentResolver
    }
    // use cases
    singleOf(::SendAnalyticsEventUseCaseImpl) { bind<SendAnalyticsEventUseCase>() }

    // view models
    viewModelOf(::MainViewModel)
}

val allModules =
    listOf(
        appModule,
        commonModule,
        appsFeatureModule,
        analysisFeatureModule,
        listenFeatureModule,
        authFeatureModule,
        onboardingFeatureModule,
        permissionFeatureModule,
        settingsFeatureModule,
        statsFeatureModule
    )
