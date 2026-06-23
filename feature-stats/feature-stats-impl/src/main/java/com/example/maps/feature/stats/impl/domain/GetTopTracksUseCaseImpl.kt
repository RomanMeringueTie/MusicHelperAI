package com.example.maps.feature.stats.impl.domain

import com.example.maps.feature.listens.api.data.repository.ListensRepository
import com.example.maps.feature.listens.api.model.TopTrack
import com.example.maps.feature.stats.api.domain.GetTopTracksUseCase

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
