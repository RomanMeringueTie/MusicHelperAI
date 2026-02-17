package com.example.music_helper.common.api.toggles

interface Toggle {
    val title: String
    val description: String
    fun changeState()
    fun isEnabled(): Boolean
}
