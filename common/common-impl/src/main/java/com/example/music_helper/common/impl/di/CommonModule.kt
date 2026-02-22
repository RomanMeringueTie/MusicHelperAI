package com.example.music_helper.common.impl.di

import com.example.music_helper.common.api.config.BuildTypeProvider
import com.example.music_helper.common.impl.config.BuildTypeProviderImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::BuildTypeProviderImpl) { bind<BuildTypeProvider>() }
}
