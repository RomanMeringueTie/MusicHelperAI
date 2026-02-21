package com.example.music_helper.feature.apps.api.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val isPicked: Boolean = false
)