package com.example.maps.ui.utils

import com.firebase.ui.auth.AuthUI

private val providers = arrayListOf(
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val signInIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(providers)
    .build()