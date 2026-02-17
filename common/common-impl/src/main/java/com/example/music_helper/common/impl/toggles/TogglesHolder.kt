package com.example.music_helper.common.impl.toggles

import com.example.music_helper.common.api.toggles.ListensFilterToggle
import com.example.music_helper.common.api.toggles.Toggle

object TogglesHolder {
    private val toggles by lazy {
        listOf(ListensFilterToggle)
    }

    fun getAll(): List<Toggle> = toggles
}
