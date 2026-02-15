package com.example.music_helper.toggles

object TogglesHolder {
    private val toggles by lazy {
        listOf(ListensFilterToggle)
    }

    fun getAll(): List<Toggle> = toggles
}