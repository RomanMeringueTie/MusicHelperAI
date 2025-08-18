package com.example.maps.data.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object UserModel {
    var name by mutableStateOf<String?>(null)
    var picture by mutableStateOf<String?>(null)
    var isAuthorized by mutableStateOf(false)
}