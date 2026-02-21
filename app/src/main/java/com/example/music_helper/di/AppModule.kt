package com.example.music_helper.di

import android.content.Context
import com.example.music_helper.common.impl.di.commonModule
import com.example.music_helper.feature.analysis.impl.di.analysisFeatureModule
import com.example.music_helper.feature.apps.impl.di.appsFeatureModule
import com.example.music_helper.feature.auth.impl.di.authFeatureModule
import com.example.music_helper.feature.listens.impl.di.listenFeatureModule
import com.example.music_helper.feature.onboarding.impl.di.onboardingFeatureModule
import com.example.music_helper.feature.permission.impl.di.permissionFeatureModule
import com.example.music_helper.feature.settings.impl.di.settingsFeatureModule
import com.example.music_helper.feature.stats.impl.di.statsFeatureModule
import com.example.music_helper.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
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
