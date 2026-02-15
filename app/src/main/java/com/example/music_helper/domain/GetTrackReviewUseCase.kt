package com.example.music_helper.domain

import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.model.TrackReview

interface GetTrackReviewUseCase {
    suspend operator fun invoke(track: ListenFull): Result<TrackReview>
}