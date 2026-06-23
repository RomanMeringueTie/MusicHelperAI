package com.example.maps.feature.analysis.api.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.common.api.presentation.State
import com.example.maps.feature.listens.api.domain.GetListensReviewUseCase
import com.example.maps.feature.listens.api.model.ListensReview
import com.example.maps.feature.listens.api.domain.GetListensUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalysisViewModel(
    private val getListensUseCase: GetListensUseCase,
    private val getListensReviewUseCase: GetListensReviewUseCase,
) :
    ViewModel() {
    private val _listensReview = MutableStateFlow<State<ListensReview>>(State.Loading)
    val listensReview = _listensReview.asStateFlow()

    init {
        viewModelScope.launch {
            _listensReview.value = State.Loading
            val listensResult = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            listensResult.fold(
                onSuccess = {
                    val result = withContext(Dispatchers.IO) {
                        getListensReviewUseCase(it.take(20).joinToString { "${it.artist} - ${it.title}" })
                    }
                    result.fold(
                        onSuccess = { _listensReview.value = State.Content(it) },
                        onFailure = {
                            _listensReview.value =
                                State.Failure(it.message ?: "Что-то пошло не так...")
                        }
                    )
                },
                onFailure = {
                    _listensReview.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )

        }
    }
}
