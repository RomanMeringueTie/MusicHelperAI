package com.example.music_helper.feature.stats.api.domain

import com.example.music_helper.feature.listens.api.model.TopArtist

interface GetTopArtistsUseCase {
    suspend operator fun invoke(): Result<List<TopArtist>>
}
