package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    getPickedAppsUseCase: GetPickedAppsUseCase,
    getPermissionUseCase: GetPermissionUseCase,
) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme
    var isPermission: Boolean = false
    var isAppsPicked: Boolean = false

    init {
        viewModelScope.launch {
            val isAppsPickedResult = withContext(Dispatchers.IO) { getPickedAppsUseCase() }
            isPermission = withContext(Dispatchers.IO) { getPermissionUseCase() }
            isAppsPickedResult.fold(
                onSuccess = {
                    isAppsPicked = true
                },
                onFailure = {
                    isAppsPicked = false
                }
            )
        }
    }

    fun changeTheme() {
        _isDarkTheme.value = _isDarkTheme.value.not()
    }
}