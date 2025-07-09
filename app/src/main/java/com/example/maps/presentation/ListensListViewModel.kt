package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.ListenFull
import com.example.maps.domain.GetListensUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListensListViewModel(
    private val getListensUseCase: GetListensUseCase
) : ViewModel() {

    private val _listens = MutableStateFlow<State<List<ListenFull>>>(State.Initial)
    val listens = _listens.asStateFlow()

    init {
        viewModelScope.launch {
            _listens.value = State.Loading
            val result = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            result.fold(
                onSuccess = {
                    _listens.value = State.Content(it)
                },
                onFailure = {
                    _listens.value = State.Failure(it.message ?: "Unknown Error")
                }
            )
        }
    }
}