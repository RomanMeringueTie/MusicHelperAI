package com.example.maps.toggles

sealed interface Toggle {
    val title: String
    val description: String
    fun changeState()
    fun isEnabled(): Boolean
}