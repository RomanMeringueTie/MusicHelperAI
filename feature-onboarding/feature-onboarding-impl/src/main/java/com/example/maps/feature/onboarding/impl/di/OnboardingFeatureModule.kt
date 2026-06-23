package com.example.maps.feature.onboarding.impl.di

import com.example.maps.feature.onboarding.api.presentation.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingFeatureModule = module {
    viewModelOf(::OnboardingViewModel)
}
