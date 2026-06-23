package com.example.maps.common.api.toggles

interface Toggle {
    val title: String
    val description: String
    fun changeState()
    fun isEnabled(): Boolean
}
