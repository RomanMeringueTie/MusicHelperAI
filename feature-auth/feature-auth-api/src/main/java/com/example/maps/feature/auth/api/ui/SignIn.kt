package com.example.maps.feature.auth.api.ui

import com.firebase.ui.auth.AuthUI

private val providers = arrayListOf(
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val signInIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(providers)
    .build()