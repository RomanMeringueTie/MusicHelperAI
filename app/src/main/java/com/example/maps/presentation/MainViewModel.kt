package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.common.api.model.SettingsSingleton
import com.example.maps.common.api.model.UserSingleton
import com.example.maps.domain.SendAnalyticsEventUseCase
import com.example.maps.feature.apps.api.domain.GetPickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class MainViewModel(
    getPickedAppsUseCase: GetPickedAppsUseCase,
    private val sendAnalyticsEventUseCase: SendAnalyticsEventUseCase,
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

    fun sendEvent(screenName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendAnalyticsEventUseCase(
                screenName = screenName,
                userId = UserSingleton.userId ?: ""
            )
        }
    }
}
