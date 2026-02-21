package com.example.music_helper.feature.onboarding.impl.di

import com.example.music_helper.feature.onboarding.api.presentation.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingFeatureModule = module {
    viewModelOf(::OnboardingViewModel)
}
