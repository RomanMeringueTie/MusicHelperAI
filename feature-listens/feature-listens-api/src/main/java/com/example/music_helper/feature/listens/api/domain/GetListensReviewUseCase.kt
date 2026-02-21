package com.example.music_helper.feature.listens.api.domain

import com.example.music_helper.feature.listens.api.model.ListensReview

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<ListensReview>
}