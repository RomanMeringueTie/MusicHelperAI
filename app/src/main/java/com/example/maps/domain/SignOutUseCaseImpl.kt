package com.example.maps.domain

import com.example.maps.data.service.AuthService

class SignOutUseCaseImpl(private val authService: AuthService): SignOutUseCase {
    override fun invoke() {
        authService.signOut()
    }
}