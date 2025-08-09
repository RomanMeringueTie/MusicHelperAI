package com.example.maps.domain

import com.example.maps.data.model.TopTrack
import com.example.maps.data.repository.ListensRepository

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