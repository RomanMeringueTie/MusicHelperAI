package com.example.maps.common.api.toggles

object TogglesHolder {
    private val toggles by lazy {
        listOf(ListensFilterToggle)
    }

    fun getAll(): List<Toggle> = toggles
}
