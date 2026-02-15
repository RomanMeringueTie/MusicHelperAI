package com.example.music_helper.domain

import com.example.music_helper.data.model.TopTrack

interface GetTopTracksUseCase {
    suspend operator fun invoke(): Result<List<TopTrack>>
} 