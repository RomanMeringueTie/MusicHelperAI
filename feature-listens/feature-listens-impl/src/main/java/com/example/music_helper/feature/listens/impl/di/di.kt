package com.example.music_helper.feature.listens.impl.di

import androidx.room.Room
import com.example.music_helper.feature.listens.api.db.AppDatabase
import com.example.music_helper.feature.listens.api.domain.DeleteListenUseCase
import com.example.music_helper.feature.listens.api.domain.GetListensUseCase
import com.example.music_helper.feature.listens.api.domain.InsertListenUseCase
import com.example.music_helper.feature.listens.impl.data.datasource.ListensLocalDataSource
import com.example.music_helper.feature.listens.impl.data.datasource.ListensLocalDataSourceImpl
import com.example.music_helper.feature.listens.impl.data.datasource.ListensRemoteDataSource
import com.example.music_helper.feature.listens.impl.data.datasource.ListensRemoteDataSourceImpl
import com.example.music_helper.feature.listens.api.data.repository.ListensRepository
import com.example.music_helper.feature.listens.impl.data.repository.ListensRepositoryImpl
import com.example.music_helper.feature.listens.impl.domain.DeleteListenUseCaseImpl
import com.example.music_helper.feature.listens.impl.domain.GetListensUseCaseImpl
import com.example.music_helper.feature.listens.impl.domain.InsertListenUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
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

    singleOf(::ListensLocalDataSourceImpl) { bind<ListensLocalDataSource>() }
    singleOf(::ListensRemoteDataSourceImpl) { bind<ListensRemoteDataSource>() }
    singleOf(::ListensRepositoryImpl) { bind<ListensRepository>() }
    singleOf(::GetListensUseCaseImpl) { bind<GetListensUseCase>() }
    singleOf(::InsertListenUseCaseImpl) { bind<InsertListenUseCase>() }
    singleOf(::DeleteListenUseCaseImpl) { bind<DeleteListenUseCase>() }
}
