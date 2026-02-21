package com.example.music_helper.feature.auth.impl.domain

import com.example.music_helper.feature.auth.api.domain.SignOutUseCase
import com.example.music_helper.feature.auth.impl.data.service.AuthService

class SignOutUseCaseImpl(private val authService: AuthService): SignOutUseCase {
    override fun invoke() {
        authService.signOut()
    }
}
