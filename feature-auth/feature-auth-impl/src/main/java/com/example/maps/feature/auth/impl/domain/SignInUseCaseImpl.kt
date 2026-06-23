package com.example.maps.feature.auth.impl.domain

import com.example.maps.feature.auth.api.domain.SignInUseCase
import com.example.maps.feature.auth.impl.data.service.AuthService

class SignInUseCaseImpl(private val authService: AuthService) : SignInUseCase {
    override fun invoke() {
        authService.signIn()
    }
}
