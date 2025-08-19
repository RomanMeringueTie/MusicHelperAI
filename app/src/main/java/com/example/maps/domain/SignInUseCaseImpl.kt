package com.example.maps.domain

import com.example.maps.data.service.AuthService
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class SignInUseCaseImpl(private val authService: AuthService) : SignInUseCase {
    override fun invoke(result: FirebaseAuthUIAuthenticationResult) {
        authService.signIn(result)
    }
}