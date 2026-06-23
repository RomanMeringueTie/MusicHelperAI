package com.example.maps.feature.listens.impl.di

import androidx.room.Room
import com.example.maps.feature.listens.api.data.db.AppDatabase
import com.example.maps.feature.listens.api.domain.DeleteListenUseCase
import com.example.maps.feature.listens.api.domain.GetListensUseCase
import com.example.maps.feature.listens.api.domain.InsertListenUseCase
import com.example.maps.feature.listens.impl.data.datasource.ListensLocalDataSource
import com.example.maps.feature.listens.impl.data.datasource.ListensLocalDataSourceImpl
import com.example.maps.feature.listens.impl.data.datasource.ListensRemoteDataSource
import com.example.maps.feature.listens.impl.data.datasource.ListensRemoteDataSourceImpl
import com.example.maps.feature.listens.api.data.repository.ListensRepository
import com.example.maps.feature.listens.api.domain.GetListensReviewUseCase
import com.example.maps.feature.listens.api.domain.GetTrackReviewUseCase
import com.example.maps.feature.listens.api.presentation.ListensListViewModel
import com.example.maps.feature.listens.impl.data.repository.ListensRepositoryImpl
import com.example.maps.feature.listens.impl.domain.DeleteListenUseCaseImpl
import com.example.maps.feature.listens.impl.domain.GetListensReviewUseCaseImpl
import com.example.maps.feature.listens.impl.domain.GetListensUseCaseImpl
import com.example.maps.feature.listens.impl.domain.GetTrackReviewUseCaseImpl
import com.example.maps.feature.listens.impl.domain.InsertListenUseCaseImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val listenFeatureModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "listens"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single { get<AppDatabase>().ListenDao() }
    single { get<AppDatabase>().ArtistDao() }
    single { get<AppDatabase>().TrackDao() }

    single {
        Firebase.firestore
    }

    singleOf(::GetTrackReviewUseCaseImpl) { bind<GetTrackReviewUseCase>() }
    singleOf(::GetListensReviewUseCaseImpl) { bind<GetListensReviewUseCase>() }
    singleOf(::ListensLocalDataSourceImpl) { bind<ListensLocalDataSource>() }
    singleOf(::ListensRemoteDataSourceImpl) { bind<ListensRemoteDataSource>() }
    singleOf(::ListensRepositoryImpl) { bind<ListensRepository>() }
    singleOf(::GetListensUseCaseImpl) { bind<GetListensUseCase>() }
    singleOf(::InsertListenUseCaseImpl) { bind<InsertListenUseCase>() }
    singleOf(::DeleteListenUseCaseImpl) { bind<DeleteListenUseCase>() }

    viewModelOf(::ListensListViewModel)
}
