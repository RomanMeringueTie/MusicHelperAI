package com.example.music_helper.presentation

import androidx.lifecycle.ViewModel
import com.example.music_helper.toggles.TogglesHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DebugPanelViewModel : ViewModel() {
    private val _isTogglesEnabled = MutableStateFlow(TogglesHolder.getAll().map { it.isEnabled() })

    val isTogglesEnabled: StateFlow<List<Boolean>>
        get() = _isTogglesEnabled.asStateFlow()


    fun changeToggleState() {
        _isTogglesEnabled.update { TogglesHolder.getAll().map { it.isEnabled() } }
    }
}