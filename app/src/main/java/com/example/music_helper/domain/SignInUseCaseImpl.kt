package com.example.music_helper.domain

import com.example.music_helper.data.service.AuthService

class SignInUseCaseImpl(private val authService: AuthService) : SignInUseCase {
    override fun invoke() {
        authService.signIn()
    }
}