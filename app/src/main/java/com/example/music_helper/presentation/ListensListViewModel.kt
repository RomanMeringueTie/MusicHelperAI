package com.example.music_helper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_helper.data.model.Day
import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.model.TrackReview
import com.example.music_helper.domain.DeleteListenUseCase
import com.example.music_helper.domain.GetListensUseCase
import com.example.music_helper.domain.GetTrackReviewUseCase
import com.example.music_helper.domain.InsertListenUseCase
import com.example.music_helper.ui.utils.groupListensByDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListensListViewModel(
    private val getListensUseCase: GetListensUseCase,
    private val deleteListenUseCase: DeleteListenUseCase,
    private val getTrackReviewUseCase: GetTrackReviewUseCase,
    private val insertListenUseCase: InsertListenUseCase,
) : ViewModel() {

    private val _days = MutableStateFlow<State<List<Day>>>(State.Loading)
    val days = _days.asStateFlow()

    private val _indexToDelete = MutableStateFlow<Pair<Int, Int>?>(null)
    val indexToDelete = _indexToDelete.asStateFlow()

    private val _isInsertDialogShown = MutableStateFlow(false)
    val isInsertDialogShown = _isInsertDialogShown.asStateFlow()

    private val _indexToAnalyze = MutableStateFlow<Pair<Int, Int>?>(null)
    val indexToAnalyze = _indexToAnalyze.asStateFlow()

    private val _trackReview = MutableStateFlow<State<TrackReview>>(State.Loading)
    val trackReview = _trackReview.asStateFlow()

    init {
        loadListens()
    }

    fun loadListens() {
        viewModelScope.launch {
            _days.update { State.Loading }
            val result = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            result.fold(
                onSuccess = { result ->
                    val days = groupListensByDay(result)
                    _days.update { State.Content(days) }
                },
                onFailure = { error ->
                    _days.update { State.Failure(error.message ?: "Unknown Error") }
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

    fun setIndexToAnalyze(dayIndex: Int, listenIndex: Int) {
        if (dayIndex < 0 && listenIndex < 0)
            _indexToAnalyze.update { null }
        else
            _indexToAnalyze.update { dayIndex to listenIndex }
    }

    fun analyzeListen() {
        viewModelScope.launch {
            _trackReview.update { State.Loading }
            val listen =
                (_days.value as State.Content).data[_indexToAnalyze.value!!.first].listens[indexToAnalyze.value!!.second]
            withContext(Dispatchers.IO) {
                val result = getTrackReviewUseCase(listen)
                result.fold(
                    onSuccess = { result ->
                        _trackReview.update { State.Content(result) }
                    },
                    onFailure = { error ->
                        _trackReview.update {
                            State.Failure(
                                error.message ?: "Что-то пошло не так..."
                            )
                        }
                    }
                )
            }
        }
    }

    fun changeInsertDialogVisibility() {
        _isInsertDialogShown.update { !_isInsertDialogShown.value }
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