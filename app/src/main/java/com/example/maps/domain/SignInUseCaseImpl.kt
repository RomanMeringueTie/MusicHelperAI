package com.example.maps.domain

import com.example.maps.data.service.AuthService

class SignInUseCaseImpl(private val authService: AuthService) : SignInUseCase {
    override fun invoke() {
        authService.signIn()
    }
}