package com.example.maps.feature.onboarding.api.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.feature.apps.api.model.AppInfo
import com.example.maps.feature.apps.api.domain.GetInstalledAppsUseCase
import com.example.maps.common.api.model.SettingsSingleton
import com.example.maps.common.api.presentation.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingViewModel(private val getInstalledAppsUseCase: GetInstalledAppsUseCase) :
    ViewModel() {

    private val _pickedApps: MutableStateFlow<State<List<AppInfo>>> =
        MutableStateFlow(State.Loading)
    val pickedApps = _pickedApps.asStateFlow()

    private val _isPermissionDialogShown = MutableStateFlow(false)
    val isPermissionDialogShown = _isPermissionDialogShown.asStateFlow()

    init {
        viewModelScope.launch {
            val pickedAppsResult = withContext(Dispatchers.IO) { getInstalledAppsUseCase() }
            pickedAppsResult.fold(
                onSuccess = {
                    _pickedApps.value = State.Content(it.filter { it.isPicked == true })
                },
                onFailure = {
                    _pickedApps.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )
        }
    }

    fun onNotificationSettingChange(isAllowed: Boolean) {
        SettingsSingleton.isNotificationsEnabled = isAllowed
    }

    fun changePermissionDialogVisibility() {
        _isPermissionDialogShown.value = !_isPermissionDialogShown.value
    }

}
