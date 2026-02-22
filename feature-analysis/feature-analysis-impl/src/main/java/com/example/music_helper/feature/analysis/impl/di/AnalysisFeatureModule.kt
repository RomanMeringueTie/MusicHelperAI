package com.example.music_helper.feature.analysis.impl.di

import com.example.music_helper.feature.analysis.api.data.datasource.AIReviewDataSource
import com.example.music_helper.feature.analysis.api.presentation.AnalysisViewModel
import com.example.music_helper.feature.analysis.impl.data.datasource.AIReviewDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val analysisFeatureModule = module {
    singleOf(::AIReviewDataSourceImpl) { bind<AIReviewDataSource>() }

    viewModelOf(::AnalysisViewModel)
}
