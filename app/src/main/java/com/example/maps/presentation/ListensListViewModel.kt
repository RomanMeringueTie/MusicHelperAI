package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.Day
import com.example.maps.data.model.ListenFull
import com.example.maps.domain.DeleteListenUseCase
import com.example.maps.domain.GetListensUseCase
import com.example.maps.domain.InsertListenUseCase
import com.example.maps.ui.utils.groupListensByDay
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

    private val _days = MutableStateFlow<State<List<Day>>>(State.Loading)
    val days = _days.asStateFlow()

    private val _indexToDelete = MutableStateFlow<Pair<Int, Int>?>(null)
    val indexToDelete = _indexToDelete.asStateFlow()

    private val _isInsertDialogShown = MutableStateFlow(false)
    val isInsertDialogShown = _isInsertDialogShown.asStateFlow()

    init {
        loadListens()
    }

    private fun loadListens() {
        viewModelScope.launch {
            _days.value = State.Loading
            val result = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            result.fold(
                onSuccess = {
                    val days = groupListensByDay(it)
                    _days.value = State.Content(days)
                },
                onFailure = {
                    _days.value = State.Failure(it.message ?: "Unknown Error")
                }
            )
        }
    }

    fun setIndexToDelete(dayIndex: Int, listenIndex: Int) {
        if (dayIndex < 0 && listenIndex < 0)
            _indexToDelete.value = null
        else
            _indexToDelete.value = dayIndex to listenIndex
    }

    fun deleteListen() {
        viewModelScope.launch {
            val listen =
                (_days.value as State.Content).data[_indexToDelete.value!!.first].listens[indexToDelete.value!!.second]
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