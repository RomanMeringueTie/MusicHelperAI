package com.example.maps.feature.settings.api.presentation

import androidx.lifecycle.ViewModel
import com.example.maps.common.api.build_type.BuildType
import com.example.maps.common.api.build_type.BuildTypeProvider
import com.example.maps.common.api.toggles.TogglesHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DebugPanelViewModel(private val buildTypeProvider: BuildTypeProvider) : ViewModel() {
    private val _isTogglesEnabled = MutableStateFlow(TogglesHolder.getAll().map { it.isEnabled() })

    val isTogglesEnabled: StateFlow<List<Boolean>>
        get() = _isTogglesEnabled.asStateFlow()


    fun changeToggleState() {
        _isTogglesEnabled.update { TogglesHolder.getAll().map { it.isEnabled() } }
    }

    fun getBuildType(): BuildType = buildTypeProvider.getBuildType()
}
