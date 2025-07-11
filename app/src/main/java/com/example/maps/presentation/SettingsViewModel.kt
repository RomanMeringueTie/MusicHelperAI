package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.AppInfo
import com.example.maps.domain.GetInstalledAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(private val getInstalledAppsUseCase: GetInstalledAppsUseCase) :
    ViewModel() {
    private val _state: MutableStateFlow<State<List<AppInfo>>> = MutableStateFlow(State.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getInstalledAppsUseCase() }
            result.fold(
                onSuccess = {
                    _state.value = State.Content(it.filter { it.isPicked == true })
                },
                onFailure = {
                    _state.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )
        }
    }
}