package com.example.maps.feature.permission.api.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.feature.permission.api.domain.GetPermissionUseCase
import com.example.maps.common.api.model.SettingsSingleton
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