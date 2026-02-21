package com.example.music_helper.feature.listens.impl.domain

import com.example.music_helper.feature.listens.api.domain.GetTrackReviewUseCase
import com.example.music_helper.feature.listens.api.model.TrackReview
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.analysis.api.data.datasource.AIReviewDataSource

class GetTrackReviewUseCaseImpl(private val aiReviewDataSource: AIReviewDataSource) :
    GetTrackReviewUseCase {
    override suspend fun invoke(track: ListenFull): Result<TrackReview> {
        try {
            val reviewPrompt = REVIEW_PROMPT.format(track.artist, track.title)
            val result = aiReviewDataSource.get(reviewPrompt)
            val trackReview = TrackReview(
                artist = track.artist,
                title = track.title,
                review = result
            )
            return Result.success(trackReview)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private companion object {
        const val REVIEW_PROMPT = "%d - %s расскажи про исполнителя и про трек, порекомендуй что-нибудь на основе этого трека"
    }
}
