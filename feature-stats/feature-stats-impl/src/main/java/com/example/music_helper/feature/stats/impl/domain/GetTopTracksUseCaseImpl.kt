package com.example.music_helper.feature.stats.impl.domain

import com.example.music_helper.feature.listens.api.data.repository.ListensRepository
import com.example.music_helper.feature.listens.api.model.TopTrack
import com.example.music_helper.feature.stats.api.domain.GetTopTracksUseCase

class GetTopTracksUseCaseImpl(private val listensRepository: ListensRepository) :
    GetTopTracksUseCase {
    override suspend operator fun invoke(): Result<List<TopTrack>> {
        try {
            val result = listensRepository.getTopTracks()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
} 
