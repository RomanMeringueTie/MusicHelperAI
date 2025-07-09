package com.example.maps.domain

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<String>
}