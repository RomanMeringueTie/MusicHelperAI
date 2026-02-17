package com.example.music_helper.common.api.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object UserSingleton {
    var name by mutableStateOf<String?>(null)
    var picture by mutableStateOf<String?>(null)
    var isAuthorized by mutableStateOf(false)
    var userId by mutableStateOf<String?>(null)
}
