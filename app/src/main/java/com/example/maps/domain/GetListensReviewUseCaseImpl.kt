package com.example.maps.domain

import com.example.maps.data.model.Review
import com.example.maps.data.datasource.ListensReviewDataSource

class GetListensReviewUseCaseImpl(private val listensReviewDataSource: ListensReviewDataSource) :
    GetListensReviewUseCase {
    override suspend fun invoke(listens: String): Result<Review> {
        try {
            val result = listensReviewDataSource.get(listens)
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}