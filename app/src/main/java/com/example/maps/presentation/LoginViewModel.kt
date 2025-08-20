package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import com.example.maps.domain.SignInUseCase

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    fun onSignIn() {
        signInUseCase()
    }
}