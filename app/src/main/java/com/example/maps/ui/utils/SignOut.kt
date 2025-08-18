package com.example.maps.ui.utils

import android.content.Context
import com.firebase.ui.auth.AuthUI

fun signOut(context: Context) {
    AuthUI.getInstance()
        .signOut(context)
}