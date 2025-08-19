package com.example.maps.domain

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

interface SignInUseCase {
    operator fun invoke(result: FirebaseAuthUIAuthenticationResult)
}