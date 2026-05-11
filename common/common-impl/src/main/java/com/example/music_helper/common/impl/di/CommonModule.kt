package com.example.music_helper.common.impl.di

import com.example.music_helper.common.api.build_type.BuildTypeProvider
import com.example.music_helper.common.api.client.HttpClient
import com.example.music_helper.common.api.model.RemoteConfig
import com.example.music_helper.common.impl.build_type.BuildTypeProviderImpl
import com.example.music_helper.common.impl.client.HttpClientImpl
import com.example.music_helper.common.impl.config.RemoteConfigImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::BuildTypeProviderImpl) { bind<BuildTypeProvider>() }
    single<HttpClient> { HttpClientImpl("https://iv222s06.hawk-dev.csc.sibsutis.ru/") }
    singleOf(::RemoteConfigImpl) { bind<RemoteConfig>() }
}
