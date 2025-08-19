package com.example.maps.data.service

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

interface AuthService {
    fun signIn(result: FirebaseAuthUIAuthenticationResult)
    fun signOut()
}