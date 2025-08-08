package com.example.maps.di

import android.content.Context
import androidx.room.Room
import com.example.maps.data.db.AppDatabase
import com.example.maps.data.repository.ListensRepository
import com.example.maps.data.repository.ListensRepositoryImpl
import com.example.maps.domain.DeleteListenUseCase
import com.example.maps.domain.DeleteListenUseCaseImpl
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.GetInstalledAppsUseCaseImpl
import com.example.maps.domain.GetListensReviewUseCase
import com.example.maps.domain.GetListensReviewUseCaseImpl
import com.example.maps.domain.GetListensUseCase
import com.example.maps.domain.GetListensUseCaseImpl
import com.example.maps.domain.GetNotificationSettingUseCase
import com.example.maps.domain.GetNotificationSettingUseCaseImpl
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPermissionUseCaseImpl
import com.example.maps.domain.GetPickedAppsUseCase
import com.example.maps.domain.GetPickedAppsUseCaseImpl
import com.example.maps.domain.GetTopArtistsUseCase
import com.example.maps.domain.GetTopArtistsUseCaseImpl
import com.example.maps.domain.GetTopTracksUseCase
import com.example.maps.domain.GetTopTracksUseCaseImpl
import com.example.maps.domain.InsertListenUseCase
import com.example.maps.domain.InsertListenUseCaseImpl
import com.example.maps.domain.SavePickedAppsUseCase
import com.example.maps.domain.SavePickedAppsUseCaseImpl
import com.example.maps.domain.SetNotificationSettingUseCase
import com.example.maps.domain.SetNotificationSettingUseCaseImpl
import com.example.maps.presentation.AnalysisViewModel
import com.example.maps.presentation.ListensListViewModel
import com.example.maps.presentation.MainViewModel
import com.example.maps.presentation.PickAppsViewModel
import com.example.maps.presentation.SettingsViewModel
import com.example.maps.presentation.StatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "listens"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single {
        val db: AppDatabase = get()
        db.ListenDao()
    }
    single {
        val db: AppDatabase = get()
        db.ArtistDao()
    }
    single {
        val db: AppDatabase = get()
        db.TrackDao()
    }
    singleOf(::ListensRepositoryImpl) { bind<ListensRepository>() }
    singleOf(::GetListensUseCaseImpl) { bind<GetListensUseCase>() }
    singleOf(::DeleteListenUseCaseImpl) { bind<DeleteListenUseCase>() }
    singleOf(::InsertListenUseCaseImpl) { bind<InsertListenUseCase>() }
    viewModelOf(::ListensListViewModel)
    single {
        androidContext().packageManager
    }
    single {
        androidContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE)
    }
    single {
        androidContext().contentResolver
    }
    singleOf(::GetNotificationSettingUseCaseImpl) { bind<GetNotificationSettingUseCase>() }
    singleOf(::SetNotificationSettingUseCaseImpl) { bind<SetNotificationSettingUseCase>() }
    singleOf(::GetListensReviewUseCaseImpl) { bind<GetListensReviewUseCase>() }
    singleOf(::GetInstalledAppsUseCaseImpl) { bind<GetInstalledAppsUseCase>() }
    singleOf(::SavePickedAppsUseCaseImpl) { bind<SavePickedAppsUseCase>() }
    singleOf(::GetPermissionUseCaseImpl) { bind<GetPermissionUseCase>() }
    singleOf(::GetPickedAppsUseCaseImpl) { bind<GetPickedAppsUseCase>() }
    singleOf(::GetTopArtistsUseCaseImpl) { bind<GetTopArtistsUseCase>() }
    singleOf(::GetTopTracksUseCaseImpl) { bind<GetTopTracksUseCase>() }
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::PickAppsViewModel)
    viewModelOf(::AnalysisViewModel)
    viewModelOf(::StatsViewModel)
}