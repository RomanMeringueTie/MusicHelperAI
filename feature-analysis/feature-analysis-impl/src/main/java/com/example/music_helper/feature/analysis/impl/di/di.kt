package com.example.music_helper.feature.analysis.impl.di

import com.example.music_helper.feature.analysis.api.domain.GetListensReviewUseCase
import com.example.music_helper.feature.analysis.api.domain.GetTrackReviewUseCase
import com.example.music_helper.feature.analysis.impl.data.datasource.AIReviewDataSource
import com.example.music_helper.feature.analysis.impl.data.datasource.AIReviewDataSourceImpl
import com.example.music_helper.feature.analysis.impl.domain.GetListensReviewUseCaseImpl
import com.example.music_helper.feature.analysis.impl.domain.GetTrackReviewUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val analysisFeatureModule = module {
    singleOf(::AIReviewDataSourceImpl) { bind<AIReviewDataSource>() }
    singleOf(::GetTrackReviewUseCaseImpl) { bind<GetTrackReviewUseCase>() }
    singleOf(::GetListensReviewUseCaseImpl) { bind<GetListensReviewUseCase>() }
}
