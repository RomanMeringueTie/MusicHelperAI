package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.Review
import com.example.maps.domain.GetListensReviewUseCase
import com.example.maps.domain.GetListensUseCase
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
    private val _review = MutableStateFlow<State<Review>>(State.Loading)
    val review = _review.asStateFlow()

    init {
        viewModelScope.launch {
            _review.value = State.Loading
            val listensResult = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            listensResult.fold(
                onSuccess = {
                    val result = withContext(Dispatchers.IO) {
                        getListensReviewUseCase(it.joinToString { "${it.artist} - ${it.title}" })
                    }
                    result.fold(
                        onSuccess = { _review.value = State.Content(it) },
                        onFailure = {
                            _review.value = State.Failure(it.message ?: "Что-то пошло не так...")
                        }
                    )
                },
                onFailure = {
                    _review.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )

        }
    }
}
