package com.example.maps.feature.auth.impl.domain

import com.example.maps.feature.auth.api.domain.SignOutUseCase
import com.example.maps.feature.auth.impl.data.service.AuthService

class SignOutUseCaseImpl(private val authService: AuthService): SignOutUseCase {
    override fun invoke() {
        authService.signOut()
    }
}
