package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.SavePickedAppsUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PickAppsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val savePickedAppsUseCase: SavePickedAppsUseCase,
) : ViewModel() {

    private val _apps = MutableStateFlow(getInstalledAppsUseCase().toPersistentList())
    val apps = _apps.asStateFlow()

    fun onAppPick(position: Int) {
        _apps.update { currentList ->
            currentList.toMutableList().also { mutableList ->
                val pickedApp = mutableList[position]
                val updatedApp = pickedApp.copy(isPicked = !pickedApp.isPicked)
                mutableList[position] = updatedApp
            }.toPersistentList()
        }
    }

    fun onSave() {
        val pickedPackages = _apps.value
            .filter { it.isPicked == true }
            .map { it.packageName }
            .toSet()
        savePickedAppsUseCase(pickedPackages)
    }
}