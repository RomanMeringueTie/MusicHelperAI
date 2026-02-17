package com.example.music_helper.feature.stats.api.domain

import com.example.music_helper.feature.listens.api.model.TopTrack

interface GetTopTracksUseCase {
    suspend operator fun invoke(): Result<List<TopTrack>>
}
