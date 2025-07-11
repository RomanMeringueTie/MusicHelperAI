package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.ListenFull
import com.example.maps.domain.DeleteListenUseCase
import com.example.maps.domain.GetListensUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListensListViewModel(
    private val getListensUseCase: GetListensUseCase,
    private val deleteListenUseCase: DeleteListenUseCase,
) : ViewModel() {

    private val _listens = MutableStateFlow<State<List<ListenFull>>>(State.Loading)
    val listens = _listens.asStateFlow()

    init {
        loadListens()
    }

    private fun loadListens() {
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

    fun deleteListen(position: Int) {
        viewModelScope.launch {
            val listen = (_listens.value as State.Content).data[position]
            withContext(Dispatchers.IO) { deleteListenUseCase(listen) }
            loadListens()
        }
    }
}