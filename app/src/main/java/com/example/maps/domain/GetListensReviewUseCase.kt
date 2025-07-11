package com.example.maps.domain

import com.example.maps.data.model.Review

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<Review>
}