package com.example.music_helper.domain

import com.example.music_helper.data.service.AuthService

class SignOutUseCaseImpl(private val authService: AuthService): SignOutUseCase {
    override fun invoke() {
        authService.signOut()
    }
}