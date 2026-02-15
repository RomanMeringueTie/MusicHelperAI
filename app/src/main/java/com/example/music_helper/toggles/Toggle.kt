package com.example.music_helper.toggles

sealed interface Toggle {
    val title: String
    val description: String
    fun changeState()
    fun isEnabled(): Boolean
}