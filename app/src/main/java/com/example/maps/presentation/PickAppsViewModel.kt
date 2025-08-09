package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.AppInfo
import com.example.maps.domain.GetInstalledAppsUseCase
import com.example.maps.domain.SetPickedAppsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PickAppsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val setPickedAppsUseCase: SetPickedAppsUseCase,
) : ViewModel() {

    private val _apps = MutableStateFlow<State<List<AppInfo>>>(State.Loading)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val apps: StateFlow<State<List<AppInfo>>> = combine(_apps, _searchQuery) { state, query ->
        when (state) {
            is State.Content -> {
                val filtered = state.data.filter {
                    it.appName.contains(query, ignoreCase = true)
                }
                State.Content(filtered)
            }

            else -> state
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State.Loading)

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

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onAppPick(packageName: String) {
        val current = _apps.value
        if (current is State.Content) {
            val actualList = current.data
            val pickedApp = actualList.find { it.packageName == packageName }!!
            val updatedApp = pickedApp.copy(isPicked = !pickedApp.isPicked)
            val updatedList = actualList.toMutableList().apply {
                val position = actualList.indexOf(pickedApp)
                this[position] = updatedApp
            }
            _apps.value = State.Content(updatedList)
        }
    }

    fun onSave() {
        val pickedPackages = (_apps.value as? State.Content)?.data
            ?.filter { it.isPicked }
            ?.map { it.packageName }
            ?.toSet()
        pickedPackages?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) { setPickedAppsUseCase(it) }
            }
        }
    }
}