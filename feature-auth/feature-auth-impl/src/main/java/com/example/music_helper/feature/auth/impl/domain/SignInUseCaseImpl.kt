package com.example.music_helper.feature.auth.impl.domain

import com.example.music_helper.feature.auth.api.SignInUseCase
import com.example.music_helper.feature.auth.impl.data.service.AuthService

class SignInUseCaseImpl(private val authService: AuthService) : SignInUseCase {
    override fun invoke() {
        authService.signIn()
    }
}
