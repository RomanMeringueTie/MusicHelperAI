package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPickedAppsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    getPickedAppsUseCase: GetPickedAppsUseCase,
    getPermissionUseCase: GetPermissionUseCase
) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme
    val isPermission: Boolean = getPermissionUseCase()
    var isAppsPicked: Boolean = false

    init {
        val result = getPickedAppsUseCase()
        result.fold(
            onSuccess = {
                isAppsPicked = true
            },
            onFailure = {
                isAppsPicked = false
            }
        )
    }

    fun changeTheme() {
        _isDarkTheme.value = _isDarkTheme.value.not()
    }
}