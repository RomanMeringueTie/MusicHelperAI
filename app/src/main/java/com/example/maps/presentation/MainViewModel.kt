package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.SettingsSingleton
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    getPickedAppsUseCase: GetPickedAppsUseCase,
) : ViewModel() {
    private val _isAppsPicked = MutableStateFlow(false)
    val isAppsPicked = _isAppsPicked.asStateFlow()

    init {
        viewModelScope.launch {
            val isAppsPickedResult = withContext(Dispatchers.IO) { getPickedAppsUseCase() }
            isAppsPickedResult.fold(
                onSuccess = {
                    _isAppsPicked.value = true
                },
                onFailure = {
                    _isAppsPicked.value = false
                }
            )
        }
    }

    fun changeTheme() {
        SettingsSingleton.isDarkTheme = !SettingsSingleton.isDarkTheme
    }
}