package com.example.music_helper.feature.auth.api.presentation

import androidx.lifecycle.ViewModel
import com.example.music_helper.feature.auth.api.domain.SignInUseCase

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    fun onSignIn() {
        signInUseCase()
    }
}