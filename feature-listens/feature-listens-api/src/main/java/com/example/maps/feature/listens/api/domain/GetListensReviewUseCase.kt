package com.example.maps.feature.listens.api.domain

import com.example.maps.feature.listens.api.model.ListensReview

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<ListensReview>
}