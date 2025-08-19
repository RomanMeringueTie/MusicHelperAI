package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import com.example.maps.domain.SignInUseCase
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class LoginViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    fun onSignIn(result: FirebaseAuthUIAuthenticationResult) {
        signInUseCase(result)
    }
}