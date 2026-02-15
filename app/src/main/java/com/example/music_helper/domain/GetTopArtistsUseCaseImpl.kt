package com.example.music_helper.domain

import com.example.music_helper.data.model.TopArtist
import com.example.music_helper.data.repository.ListensRepository

class GetTopArtistsUseCaseImpl(private val listensRepository: ListensRepository) :
    GetTopArtistsUseCase {
    override suspend operator fun invoke(): Result<List<TopArtist>> {
        try {
            val result = listensRepository.getTopArtists()
            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}