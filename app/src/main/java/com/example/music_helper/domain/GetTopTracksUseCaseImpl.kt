package com.example.music_helper.domain

import com.example.music_helper.data.model.TopTrack
import com.example.music_helper.data.repository.ListensRepository

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