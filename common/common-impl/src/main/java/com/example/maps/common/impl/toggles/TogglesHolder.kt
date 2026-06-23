package com.example.maps.common.impl.toggles

import com.example.maps.common.api.toggles.ListensFilterToggle
import com.example.maps.common.api.toggles.Toggle

object TogglesHolder {
    private val toggles by lazy {
        listOf(ListensFilterToggle)
    }

    fun getAll(): List<Toggle> = toggles
}
