package com.example.music_helper.common.api.toggles

object TogglesHolder {
    private val toggles by lazy {
        listOf(ListensFilterToggle)
    }

    fun getAll(): List<Toggle> = toggles
}
