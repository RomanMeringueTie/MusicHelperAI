package com.example.music_helper.feature.auth.impl.di

import com.example.music_helper.feature.auth.api.domain.SignInUseCase
import com.example.music_helper.feature.auth.api.domain.SignOutUseCase
import com.example.music_helper.feature.auth.api.presentation.LoginViewModel
import com.example.music_helper.feature.auth.impl.data.service.AuthService
import com.example.music_helper.feature.auth.impl.data.service.AuthServiceImpl
import com.example.music_helper.feature.auth.impl.domain.SignInUseCaseImpl
import com.example.music_helper.feature.auth.impl.domain.SignOutUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authFeatureModule = module {
    singleOf(::AuthServiceImpl) { bind<AuthService>() }
    singleOf(::SignInUseCaseImpl) { bind<SignInUseCase>() }
    singleOf(::SignOutUseCaseImpl) { bind<SignOutUseCase>() }

    viewModelOf(::LoginViewModel)
}
