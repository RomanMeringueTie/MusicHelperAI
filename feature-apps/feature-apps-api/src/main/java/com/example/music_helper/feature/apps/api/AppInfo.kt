package com.example.music_helper.feature.apps.api

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val isPicked: Boolean = false
)
