package com.example.music_helper.domain

import com.example.music_helper.data.model.TopArtist

interface GetTopArtistsUseCase {
    suspend operator fun invoke(): Result<List<TopArtist>>
}