package com.example.music_helper.feature.auth.api.ui

import com.firebase.ui.auth.AuthUI

private val providers = arrayListOf(
    AuthUI.IdpConfig.GoogleBuilder().build(),
)

val signInIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setAvailableProviders(providers)
    .build()