package com.example.maps.feature.stats.api.domain

import com.example.maps.feature.listens.api.model.TopTrack

interface GetTopTracksUseCase {
    suspend operator fun invoke(): Result<List<TopTrack>>
}
