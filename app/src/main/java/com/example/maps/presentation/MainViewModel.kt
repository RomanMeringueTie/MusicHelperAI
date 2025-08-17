package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.domain.GetPermissionUseCase
import com.example.maps.domain.GetPickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    getPickedAppsUseCase: GetPickedAppsUseCase,
    getPermissionUseCase: GetPermissionUseCase,
) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()
    private val _isPermission = MutableStateFlow(false)
    val isPermission = _isPermission.asStateFlow()
    private val _isAppsPicked = MutableStateFlow(false)
    val isAppsPicked = _isAppsPicked.asStateFlow()

    init {
        viewModelScope.launch {
            _isPermission.value = withContext(Dispatchers.IO) { getPermissionUseCase() }
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
        _isDarkTheme.value = _isDarkTheme.value.not()
    }
}