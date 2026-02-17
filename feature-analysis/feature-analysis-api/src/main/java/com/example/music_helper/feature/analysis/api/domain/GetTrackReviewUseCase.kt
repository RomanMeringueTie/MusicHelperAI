package com.example.music_helper.feature.analysis.api.domain

import com.example.music_helper.feature.analysis.api.model.TrackReview
import com.example.music_helper.feature.listens.api.model.ListenFull

interface GetTrackReviewUseCase {
    suspend operator fun invoke(track: ListenFull): Result<TrackReview>
}