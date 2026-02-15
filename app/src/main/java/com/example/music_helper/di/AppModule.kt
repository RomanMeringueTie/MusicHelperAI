package com.example.music_helper.di

import android.content.Context
import androidx.room.Room
import com.example.music_helper.data.datasource.AIReviewDataSource
import com.example.music_helper.data.datasource.AIReviewDataSourceImpl
import com.example.music_helper.data.datasource.InstalledAppsDataSource
import com.example.music_helper.data.datasource.InstalledAppsDataSourceImpl
import com.example.music_helper.data.datasource.ListensLocalDataSource
import com.example.music_helper.data.datasource.ListensLocalDataSourceImpl
import com.example.music_helper.data.datasource.ListensRemoteDataSource
import com.example.music_helper.data.datasource.ListensRemoteDataSourceImpl
import com.example.music_helper.data.datasource.PermissionDataSource
import com.example.music_helper.data.datasource.PermissionDataSourceImpl
import com.example.music_helper.data.datasource.PickedAppsDataSource
import com.example.music_helper.data.datasource.PickedAppsDataSourceImpl
import com.example.music_helper.data.datasource.SettingsDataSource
import com.example.music_helper.data.datasource.SettingsDataSourceImpl
import com.example.music_helper.data.datasource.UserDataSource
import com.example.music_helper.data.datasource.UserDataSourceImpl
import com.example.music_helper.data.db.AppDatabase
import com.example.music_helper.data.repository.ListensRepository
import com.example.music_helper.data.repository.ListensRepositoryImpl
import com.example.music_helper.data.service.AuthService
import com.example.music_helper.data.service.AuthServiceImpl
import com.example.music_helper.domain.DeleteListenUseCase
import com.example.music_helper.domain.DeleteListenUseCaseImpl
import com.example.music_helper.domain.GetInstalledAppsUseCase
import com.example.music_helper.domain.GetInstalledAppsUseCaseImpl
import com.example.music_helper.domain.GetListensReviewUseCase
import com.example.music_helper.domain.GetListensReviewUseCaseImpl
import com.example.music_helper.domain.GetListensUseCase
import com.example.music_helper.domain.GetListensUseCaseImpl
import com.example.music_helper.domain.GetPermissionUseCase
import com.example.music_helper.domain.GetPermissionUseCaseImpl
import com.example.music_helper.domain.GetPickedAppsUseCase
import com.example.music_helper.domain.GetPickedAppsUseCaseImpl
import com.example.music_helper.domain.GetTopArtistsUseCase
import com.example.music_helper.domain.GetTopArtistsUseCaseImpl
import com.example.music_helper.domain.GetTopTracksUseCase
import com.example.music_helper.domain.GetTopTracksUseCaseImpl
import com.example.music_helper.domain.GetTrackReviewUseCase
import com.example.music_helper.domain.GetTrackReviewUseCaseImpl
import com.example.music_helper.domain.GetUserUseCase
import com.example.music_helper.domain.GetUserUseCaseImpl
import com.example.music_helper.domain.InsertListenUseCase
import com.example.music_helper.domain.InsertListenUseCaseImpl
import com.example.music_helper.domain.SaveUserUseCase
import com.example.music_helper.domain.SaveUserUseCaseImpl
import com.example.music_helper.domain.SetPickedAppsUseCase
import com.example.music_helper.domain.SetPickedAppsUseCaseImpl
import com.example.music_helper.domain.SignInUseCase
import com.example.music_helper.domain.SignInUseCaseImpl
import com.example.music_helper.domain.SignOutUseCase
import com.example.music_helper.domain.SignOutUseCaseImpl
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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // room
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

    // repositories
    singleOf(::ListensRepositoryImpl) { bind<ListensRepository>() }

    // services
    singleOf(::AuthServiceImpl) { bind<AuthService>() }

    // data sources
    singleOf(::SettingsDataSourceImpl) { bind<SettingsDataSource>() }
    singleOf(::ListensLocalDataSourceImpl) { bind<ListensLocalDataSource>() }
    singleOf(::ListensRemoteDataSourceImpl) { bind<ListensRemoteDataSource>() }
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::InstalledAppsDataSourceImpl) { bind<InstalledAppsDataSource>() }
    singleOf(::AIReviewDataSourceImpl) { bind<AIReviewDataSource>() }
    singleOf(::PermissionDataSourceImpl) { bind<PermissionDataSource>() }
    singleOf(::PickedAppsDataSourceImpl) { bind<PickedAppsDataSource>() }

    // use cases
    singleOf(::GetListensUseCaseImpl) { bind<GetListensUseCase>() }
    singleOf(::DeleteListenUseCaseImpl) { bind<DeleteListenUseCase>() }
    singleOf(::InsertListenUseCaseImpl) { bind<InsertListenUseCase>() }
    singleOf(::SaveUserUseCaseImpl) { bind<SaveUserUseCase>() }
    singleOf(::GetTrackReviewUseCaseImpl) { bind<GetTrackReviewUseCase>() }
    singleOf(::GetUserUseCaseImpl) { bind<GetUserUseCase>() }
    singleOf(::SignInUseCaseImpl) { bind<SignInUseCase>() }
    singleOf(::SignOutUseCaseImpl) { bind<SignOutUseCase>() }
    singleOf(::GetListensReviewUseCaseImpl) { bind<GetListensReviewUseCase>() }
    singleOf(::GetInstalledAppsUseCaseImpl) { bind<GetInstalledAppsUseCase>() }
    singleOf(::SetPickedAppsUseCaseImpl) { bind<SetPickedAppsUseCase>() }
    singleOf(::GetPermissionUseCaseImpl) { bind<GetPermissionUseCase>() }
    singleOf(::GetPickedAppsUseCaseImpl) { bind<GetPickedAppsUseCase>() }
    singleOf(::GetTopArtistsUseCaseImpl) { bind<GetTopArtistsUseCase>() }
    singleOf(::GetTopTracksUseCaseImpl) { bind<GetTopTracksUseCase>() }

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