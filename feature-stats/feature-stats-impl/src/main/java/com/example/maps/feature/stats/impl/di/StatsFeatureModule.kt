package com.example.maps.feature.stats.impl.di

import com.example.maps.feature.stats.api.domain.GetTopArtistsUseCase
import com.example.maps.feature.stats.api.domain.GetTopTracksUseCase
import com.example.maps.feature.stats.api.presentation.StatsViewModel
import com.example.maps.feature.stats.impl.domain.GetTopArtistsUseCaseImpl
import com.example.maps.feature.stats.impl.domain.GetTopTracksUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statsFeatureModule = module {
    singleOf(::GetTopArtistsUseCaseImpl) { bind<GetTopArtistsUseCase>() }
    singleOf(::GetTopTracksUseCaseImpl) { bind<GetTopTracksUseCase>() }

    viewModelOf(::StatsViewModel)
}
