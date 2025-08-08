package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.AppInfo
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.GetNotificationSettingUseCase
import com.example.maps.domain.SetNotificationSettingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val getNotificationSettingUseCase: GetNotificationSettingUseCase,
    private val setNotificationSettingUseCase: SetNotificationSettingUseCase,
) :
    ViewModel() {
    private val _pickedApps: MutableStateFlow<State<List<AppInfo>>> =
        MutableStateFlow(State.Loading)
    val pickedApps = _pickedApps.asStateFlow()

    private val _isNotificationsAllowed = MutableStateFlow(false)
    val isNotificationsAllowed = _isNotificationsAllowed.asStateFlow()

    init {
        viewModelScope.launch {
            val pickedAppsResult = withContext(Dispatchers.IO) { getInstalledAppsUseCase() }
            _isNotificationsAllowed.value =
                withContext(Dispatchers.IO) { getNotificationSettingUseCase() }

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
        viewModelScope.launch {
            withContext(Dispatchers.IO) { setNotificationSettingUseCase(isAllowed) }
        }
        _isNotificationsAllowed.value = isAllowed
    }
}