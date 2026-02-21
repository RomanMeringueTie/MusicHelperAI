package com.example.music_helper.feature.settings.api.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_helper.common.api.model.SettingsSingleton
import com.example.music_helper.common.api.presentation.State
import com.example.music_helper.feature.apps.api.model.AppInfo
import com.example.music_helper.feature.apps.api.domain.GetInstalledAppsUseCase
import com.example.music_helper.feature.auth.api.domain.SignOutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val signOutUseCase: SignOutUseCase,
) :
    ViewModel() {
    private val _pickedApps: MutableStateFlow<State<List<AppInfo>>> =
        MutableStateFlow(State.Loading)
    val pickedApps = _pickedApps.asStateFlow()

    private val _isSignOutDialogShown = MutableStateFlow(false)
    val isSignOutDialogShown = _isSignOutDialogShown.asStateFlow()

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

    fun changeSignOutDialogVisibility() {
        _isSignOutDialogShown.value = !_isSignOutDialogShown.value
    }

    fun changePermissionDialogVisibility() {
        _isPermissionDialogShown.value = !_isPermissionDialogShown.value
    }

    fun onSignOut() {
        signOutUseCase()
        changeSignOutDialogVisibility()
    }
}