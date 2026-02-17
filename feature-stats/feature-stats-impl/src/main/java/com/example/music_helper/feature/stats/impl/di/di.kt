package com.example.music_helper.feature.stats.impl.di

import com.example.music_helper.feature.stats.api.domain.GetTopArtistsUseCase
import com.example.music_helper.feature.stats.api.domain.GetTopTracksUseCase
import com.example.music_helper.feature.stats.impl.domain.GetTopArtistsUseCaseImpl
import com.example.music_helper.feature.stats.impl.domain.GetTopTracksUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val statsFeatureModule = module {
    singleOf(::GetTopArtistsUseCaseImpl) { bind<GetTopArtistsUseCase>() }
    singleOf(::GetTopTracksUseCaseImpl) { bind<GetTopTracksUseCase>() }
}
