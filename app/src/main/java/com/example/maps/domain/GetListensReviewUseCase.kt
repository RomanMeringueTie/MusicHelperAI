package com.example.maps.domain

import com.example.maps.data.model.ListensReview

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<ListensReview>
}