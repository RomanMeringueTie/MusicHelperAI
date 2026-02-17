package com.example.music_helper.feature.analysis.api.domain

import com.example.music_helper.feature.analysis.api.model.ListensReview

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<ListensReview>
}