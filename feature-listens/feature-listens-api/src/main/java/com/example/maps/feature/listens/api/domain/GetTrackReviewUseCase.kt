package com.example.maps.feature.listens.api.domain

import com.example.maps.feature.listens.api.model.TrackReview
import com.example.maps.feature.listens.api.model.ListenFull

interface GetTrackReviewUseCase {
    suspend operator fun invoke(track: ListenFull): Result<TrackReview>
}