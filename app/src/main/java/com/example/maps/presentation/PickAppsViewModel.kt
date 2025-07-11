package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.AppInfo
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.SavePickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PickAppsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val savePickedAppsUseCase: SavePickedAppsUseCase,
) : ViewModel() {

    private val _apps = MutableStateFlow<State<List<AppInfo>>>(State.Loading)
    val apps = _apps.asStateFlow()

    init {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getInstalledAppsUseCase() }
            result.fold(
                onSuccess = {
                    _apps.value = State.Content(it)
                },
                onFailure = {
                    _apps.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )
        }
    }

    fun onAppPick(position: Int) {
        val pickedApp = (_apps.value as State.Content).data[position]
        val updatedApp = pickedApp.copy(isPicked = !pickedApp.isPicked)
        val updatedList = (_apps.value as State.Content).data.toMutableList().apply {
            this[position] = updatedApp
        }
        _apps.value = State.Content(updatedList)
    }

    fun onSave() {
        val pickedPackages = (_apps.value as State.Content).data
            .filter { it.isPicked == true }
            .map { it.packageName }
            .toSet()
        savePickedAppsUseCase(pickedPackages)
    }
}