package com.example.maps.di

import android.content.Context
import androidx.room.Room
import com.example.maps.data.db.AppDatabase
import com.example.maps.data.repository.ListensRepository
import com.example.maps.data.repository.ListensRepositoryImpl
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.GetInstalledAppsUseCaseImpl
import com.example.maps.domain.GetListensReviewUseCase
import com.example.maps.domain.GetListensReviewUseCaseImpl
import com.example.maps.domain.GetListensUseCase
import com.example.maps.domain.GetListensUseCaseImpl
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPermissionUseCaseImpl
import com.example.maps.domain.GetPickedAppsUseCase
import com.example.maps.domain.GetPickedAppsUseCaseImpl
import com.example.maps.domain.SavePickedAppsUseCase
import com.example.maps.domain.SavePickedAppsUseCaseImpl
import com.example.maps.presentation.AnalysisViewModel
import com.example.maps.presentation.ListensListViewModel
import com.example.maps.presentation.MainViewModel
import com.example.maps.presentation.PickAppsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
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
    singleOf(::GetListensReviewUseCaseImpl) { bind<GetListensReviewUseCase>() }
    singleOf(::GetInstalledAppsUseCaseImpl) { bind<GetInstalledAppsUseCase>() }
    singleOf(::SavePickedAppsUseCaseImpl) { bind<SavePickedAppsUseCase>() }
    singleOf(::GetPermissionUseCaseImpl) { bind<GetPermissionUseCase>() }
    singleOf(::GetPickedAppsUseCaseImpl) { bind<GetPickedAppsUseCase>() }
    viewModelOf(::MainViewModel)
    viewModelOf(::PickAppsViewModel)
    viewModel { (listens: String) -> AnalysisViewModel(listens, get()) }
}