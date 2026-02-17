package com.example.music_helper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.feature.permission.api.domain.GetPermissionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AskPermissionViewModel(private val getPermissionUseCase: GetPermissionUseCase) : ViewModel() {

    fun checkPermission() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getPermissionUseCase()
            }
            SettingsSingleton.isNotificationsEnabled = result
        }
    }
}