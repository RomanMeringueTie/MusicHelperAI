package com.example.maps.domain

import com.example.maps.data.model.ListenFull
import com.example.maps.data.model.TrackReview

interface GetTrackReviewUseCase {
    suspend operator fun invoke(track: ListenFull): Result<TrackReview>
}