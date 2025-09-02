package com.example.maps.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maps.data.model.ListensReview
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
    private val _Listens_review = MutableStateFlow<State<ListensReview>>(State.Loading)
    val review = _Listens_review.asStateFlow()

    init {
        viewModelScope.launch {
            _Listens_review.value = State.Loading
            val listensResult = withContext(Dispatchers.IO) {
                getListensUseCase()
            }
            listensResult.fold(
                onSuccess = {
                    val result = withContext(Dispatchers.IO) {
                        getListensReviewUseCase(it.joinToString { "${it.artist} - ${it.title}" })
                    }
                    result.fold(
                        onSuccess = { _Listens_review.value = State.Content(it) },
                        onFailure = {
                            _Listens_review.value = State.Failure(it.message ?: "Что-то пошло не так...")
                        }
                    )
                },
                onFailure = {
                    _Listens_review.value = State.Failure(it.message ?: "Что-то пошло не так...")
                }
            )

        }
    }
}
