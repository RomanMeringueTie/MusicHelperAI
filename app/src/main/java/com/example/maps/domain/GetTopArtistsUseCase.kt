package com.example.maps.domain

import com.example.maps.data.model.TopArtist

interface GetTopArtistsUseCase {
    suspend operator fun invoke(): Result<List<TopArtist>>
}