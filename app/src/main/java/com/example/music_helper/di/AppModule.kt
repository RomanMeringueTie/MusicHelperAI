package com.example.music_helper.di

import android.content.Context
import com.example.music_helper.feature.analysis.impl.di.analysisFeatureModule
import com.example.music_helper.feature.apps.impl.di.appsFeatureModule
import com.example.music_helper.feature.auth.impl.di.authFeatureModule
import com.example.music_helper.feature.listens.impl.di.listenFeatureModule
import com.example.music_helper.feature.permission.impl.di.permissionFeatureModule
import com.example.music_helper.feature.settings.impl.di.settingsFeatureModule
import com.example.music_helper.feature.stats.impl.di.statsFeatureModule
import com.example.music_helper.presentation.AnalysisViewModel
import com.example.music_helper.presentation.AskPermissionViewModel
import com.example.music_helper.presentation.DebugPanelViewModel
import com.example.music_helper.presentation.FirstTimeRunViewModel
import com.example.music_helper.presentation.ListensListViewModel
import com.example.music_helper.presentation.LoginViewModel
import com.example.music_helper.presentation.MainViewModel
import com.example.music_helper.presentation.PickAppsViewModel
import com.example.music_helper.presentation.SettingsViewModel
import com.example.music_helper.presentation.StatsViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    //firebase
    single {
        Firebase.firestore
    }

    // view models
    viewModelOf(::ListensListViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::DebugPanelViewModel)
    viewModelOf(::PickAppsViewModel)
    viewModelOf(::AnalysisViewModel)
    viewModelOf(::StatsViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::AskPermissionViewModel)
    viewModelOf(::FirstTimeRunViewModel)
}

val allModules =
    listOf(
        appModule,
        appsFeatureModule,
        analysisFeatureModule,
        listenFeatureModule,
        authFeatureModule,
        permissionFeatureModule,
        settingsFeatureModule,
        statsFeatureModule
    )
