package com.example.maps.domain

import com.example.maps.data.model.TopTrack

interface GetTopTracksUseCase {
    suspend operator fun invoke(): Result<List<TopTrack>>
} 