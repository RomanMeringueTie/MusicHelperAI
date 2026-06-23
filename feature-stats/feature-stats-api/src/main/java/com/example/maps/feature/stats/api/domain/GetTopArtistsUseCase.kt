package com.example.maps.feature.stats.api.domain

import com.example.maps.feature.listens.api.model.TopArtist

interface GetTopArtistsUseCase {
    suspend operator fun invoke(): Result<List<TopArtist>>
}
