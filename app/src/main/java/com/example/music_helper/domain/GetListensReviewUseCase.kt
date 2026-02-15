package com.example.music_helper.domain

import com.example.music_helper.data.model.ListensReview

interface GetListensReviewUseCase {
    suspend operator fun invoke(listens: String): Result<ListensReview>
}