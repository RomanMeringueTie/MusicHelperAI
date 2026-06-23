package com.example.maps.common.impl.di

import com.example.maps.common.api.build_type.BuildTypeProvider
import com.example.maps.common.api.client.HttpClient
import com.example.maps.common.api.model.RemoteConfig
import com.example.maps.common.impl.build_type.BuildTypeProviderImpl
import com.example.maps.common.impl.client.HttpClientImpl
import com.example.maps.common.impl.config.RemoteConfigImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::BuildTypeProviderImpl) { bind<BuildTypeProvider>() }
    single<HttpClient> { HttpClientImpl("") }
    singleOf(::RemoteConfigImpl) { bind<RemoteConfig>() }
}
