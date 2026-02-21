package com.example.music_helper.feature.auth.api.ui

import android.content.Context
import com.firebase.ui.auth.AuthUI

fun signOut(context: Context) {
    AuthUI.getInstance()
        .signOut(context)
}