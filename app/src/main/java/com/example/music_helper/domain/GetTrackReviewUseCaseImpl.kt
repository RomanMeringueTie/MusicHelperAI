package com.example.music_helper.domain

import com.example.music_helper.data.datasource.AIReviewDataSource
import com.example.music_helper.data.model.ListenFull
import com.example.music_helper.data.model.TrackReview

class GetTrackReviewUseCaseImpl(private val aiReviewDataSource: AIReviewDataSource) :
    GetTrackReviewUseCase {
    override suspend fun invoke(track: ListenFull): Result<TrackReview> {
        try {
            val prompt =
                "${track.artist} - ${track.title} расскажи про исполнителя и про трек, порекомендуй что-нибудь на основе этого трека"
            val result = aiReviewDataSource.get(prompt)
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
}