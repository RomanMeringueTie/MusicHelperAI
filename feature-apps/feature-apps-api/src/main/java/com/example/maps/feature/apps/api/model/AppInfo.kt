package com.example.maps.feature.apps.api.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val isPicked: Boolean = false
)