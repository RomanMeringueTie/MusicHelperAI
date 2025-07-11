package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.ListenFull
import com.example.maps.domain.DeleteListenUseCase
import com.example.maps.domain.GetListensUseCase
import com.example.maps.domain.InsertListenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListensListViewModel(
    private val getListensUseCase: GetListensUseCase,
    private val deleteListenUseCase: DeleteListenUseCase,
    private val insertListenUseCase: InsertListenUseCase,
) : ViewModel() {

    private val _listens = MutableStateFlow<State<List<ListenFull>>>(State.Loading)
    val listens = _listens.asStateFlow()

    private val _indexToDelete = MutableStateFlow<Int?>(null)
    val indexToDelete = _indexToDelete.asStateFlow()

    private val _isInsertDialogShown = MutableStateFlow(false)
    val isInsertDialogShown = _isInsertDialogShown.asStateFlow()

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

    fun setIndexToDelete(index: Int?) {
        _indexToDelete.value = index
    }

    fun deleteListen() {
        viewModelScope.launch {
            val listen = (_listens.value as State.Content).data[_indexToDelete.value!!]
            _indexToDelete.value = null
            withContext(Dispatchers.IO) { deleteListenUseCase(listen) }
            loadListens()
        }
    }

    fun changeInsertDialogVisibility() {
        _isInsertDialogShown.value = !_isInsertDialogShown.value
    }

    fun insertListen(artist: String, title: String) {
        viewModelScope.launch {
            val listen = ListenFull(
                artist = artist,
                title = title,
                playedAt = System.currentTimeMillis()
            )
            withContext(Dispatchers.IO) { insertListenUseCase(listen) }
            loadListens()
        }
    }
}